package cotato.growingpain.auth.service;

import cotato.growingpain.auth.domain.BlackList;
import cotato.growingpain.auth.dto.request.ChangePasswordRequest;
import cotato.growingpain.auth.dto.request.CompleteSignupRequest;
import cotato.growingpain.auth.dto.request.LoginRequest;
import cotato.growingpain.auth.dto.request.LogoutRequest;
import cotato.growingpain.auth.dto.request.ResetPasswordRequest;
import cotato.growingpain.auth.dto.response.ResetPasswordResponse;
import cotato.growingpain.auth.repository.BlackListRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.MemberRole;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.security.RefreshTokenRepository;
import cotato.growingpain.security.jwt.JwtTokenProvider;
import cotato.growingpain.security.jwt.RefreshTokenEntity;
import cotato.growingpain.security.jwt.Token;
import cotato.growingpain.security.jwt.dto.request.ReissueRequest;
import cotato.growingpain.security.jwt.dto.response.ReissueResponse;
import cotato.growingpain.security.oauth.AuthProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final ValidateService validateService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListRepository blackListRepository;

    @Transactional
    public Token createLoginInfo(AuthProvider authProvider, LoginRequest request) {

        Optional<Member> existingMember = memberRepository.findByEmail(request.email());

        if (existingMember.isPresent()) {

            // 기존 회원이 존재하면 로그인 처리
            Member member = existingMember.get();
            if (!bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
                throw new AppException(ErrorCode.INVALID_PASSWORD);
            }

            String role = (member.getMemberRole() == MemberRole.PENDING)
                    ? MemberRole.PENDING.getDescription()
                    : MemberRole.MEMBER.getDescription();

            Token token = jwtTokenProvider.createToken(member.getId(), member.getEmail(), role);

            // RefreshTokenEntity 저장 또는 업데이트
            saveOrUpdateRefreshToken(member.getEmail(), token.getRefreshToken());

            return token;
        }
        else {
            // 신규 회원일 경우 회원가입 처리
            validateService.checkPasswordPattern(request.password());
            validateService.checkDuplicateEmail(request.email());

            log.info("[회원 가입 서비스]: {}", request.email());

            Member member = registerMember(authProvider, request.email(), request.password());

            // 회원가입 성공 후 토큰 생성 및 반환
            Token token = jwtTokenProvider.createToken(member.getId(), member.getEmail(), MemberRole.PENDING.getDescription());

            saveOrUpdateRefreshToken(member.getEmail(), token.getRefreshToken());

            return token;
        }
    }

    private Member registerMember(AuthProvider authProvider, String email, String password) {
        Member.MemberBuilder memberBuilder = Member.builder()
                .email(email)
                .authProvider(authProvider)
                .memberRole(MemberRole.PENDING);

        if (authProvider == AuthProvider.GENERAL) {
            memberBuilder.password(bCryptPasswordEncoder.encode(password));
        }

        return memberRepository.save(memberBuilder.build());
    }

    @Transactional
    public Token completeSignup(CompleteSignupRequest request, String accessToken) {

        // 토큰에서 이메일 추출
        String email = jwtTokenProvider.getEmail(accessToken);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        log.info("추가 정보 입력 받는 이메일: {}", email);

        if(member.getMemberRole() == MemberRole.PENDING){
            // 필드를 개별적으로 업데이트
            member.updateMemberInfo(request.name(), request.field(), request.belong(),request.job());
            member.updateRole(MemberRole.MEMBER);
            memberRepository.save(member);

            Token token = jwtTokenProvider.createToken(member.getId(), member.getEmail(), MemberRole.MEMBER.getDescription());

            // RefreshTokenEntity 저장
            saveOrUpdateRefreshToken(member.getEmail(), token.getRefreshToken());

            return token;
        }
        log.info("memberRole = {}", member.getMemberRole());
        return null;
    }

    public String resolveAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", ""); // "Bearer " 부분을 제거
        }
        return null;
    }

    @Transactional
    public ReissueResponse tokenReissue(ReissueRequest request) {

        String email = jwtTokenProvider.getEmail(request.refreshToken());
        String role = jwtTokenProvider.getRole(request.refreshToken());
        Long memberId = jwtTokenProvider.getMemberId(request.refreshToken());

        log.info("재발급 요청된 이메일: {}", email);
        log.info("재발급 요청된 role: {}", role);

        // 데이터베이스에서 이메일 확인 로그 추가
        RefreshTokenEntity findToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        if (jwtTokenProvider.isExpired(request.refreshToken())) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        if (!findToken.getRefreshToken().equals(request.refreshToken())) {
            log.warn("[쿠키로 들어온 토큰과 DB의 토큰이 일치하지 않음.]");
            throw new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
        }

        Token token = jwtTokenProvider.createToken(memberId, email, role);

        log.info("재발급 된 액세스 토큰: {}", token.getAccessToken());
        log.info("재발급 된 refresh 토큰: {}", token.getRefreshToken());

        // RefreshTokenEntity 업데이트
        saveOrUpdateRefreshToken(email, token.getRefreshToken());
        return ReissueResponse.from(token.getAccessToken(),token.getRefreshToken());
    }

    private void saveOrUpdateRefreshToken(String email, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(email)
                .orElse(RefreshTokenEntity.builder().email(email).build());

        refreshTokenEntity.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public void logout(LogoutRequest request) {
        String email = jwtTokenProvider.getEmail(request.refreshToken());

        RefreshTokenEntity existRefreshToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST));

        setBlackList(request.refreshToken());
        log.info("[로그아웃 된 리프레시 토큰 블랙리스트 처리]");
        refreshTokenRepository.delete(existRefreshToken);
        log.info("삭제 요청된 refreshToken: {}", request.refreshToken());
    }

    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request){

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        String tempPassword = generateTemporaryPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(tempPassword);

        log.info("임시 비밀번호 생성: {}", tempPassword);
        log.info("암호화된 비밀번호: {}", encodedPassword);

        validateService.checkPasswordPattern(tempPassword);

        member.updatePassword(encodedPassword);
        memberRepository.save(member);

        log.info("비밀번호 업데이트 완료: {}", member.getEmail());

        return new ResetPasswordResponse(tempPassword);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        // 기존 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // 새 비밀번호 패턴 검증
        validateService.checkPasswordPattern(request.newPassword());

        // 새 비밀번호로 업데이트
        String encodedNewPassword = bCryptPasswordEncoder.encode(request.newPassword());
        member.updatePassword(encodedNewPassword);
        memberRepository.save(member);

        log.info("비밀번호 번경 완료: {}", memberId);
    }

    private String generateTemporaryPassword() {
        String tempPassword;
        do {
            tempPassword = RandomStringUtils.randomAlphanumeric(8 + (int) (Math.random() * 9)); // 8-16 characters
        } while (!validateService.isValidPassword(tempPassword));
        return tempPassword;
    }

    private void setBlackList(String token) {
        BlackList blackList = BlackList.builder()
                .id(token)
                .ttl(jwtTokenProvider.getExpiration(token))
                .build();
        blackListRepository.save(blackList);
    }
}