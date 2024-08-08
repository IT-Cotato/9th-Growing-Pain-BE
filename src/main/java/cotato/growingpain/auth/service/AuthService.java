package cotato.growingpain.auth.service;

import cotato.growingpain.auth.domain.BlackList;
import cotato.growingpain.auth.dto.request.ChangePasswordRequest;
import cotato.growingpain.auth.dto.request.CompleteSignupRequest;
import cotato.growingpain.auth.dto.request.LoginRequest;
import cotato.growingpain.auth.dto.request.LogoutRequest;
import cotato.growingpain.auth.dto.response.ChangePasswordResponse;
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
    public Token createLoginInfo(LoginRequest request) {

        Optional<Member> existingMember = memberRepository.findByEmail(request.email());

        if (existingMember.isPresent()) {

            // 기존 회원이 존재하면 로그인 처리
            Member member = existingMember.get();
            if (!bCryptPasswordEncoder.matches(request.password(), member.getPassword())) {
                throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
            }

            if (member.getName() == null) {
                return jwtTokenProvider.createToken(member.getId(), request.email(), "ROLE_INCOMPLETE");
            }

            // 토큰 생성 및 반환
            return jwtTokenProvider.createToken(member.getId(), request.email(), "ROLE_MEMBER");
        }
        else {
            // 신규 회원일 경우 회원가입 처리
            validateService.checkPasswordPattern(request.password());
            validateService.checkDuplicateEmail(request.email());

            log.info("[회원 가입 서비스]: {}", request.email());

            Member newMember = Member.builder()
                    .password(bCryptPasswordEncoder.encode(request.password()))
                    .email(request.email())
                    .build();
            memberRepository.save(newMember);

        // 회원가입 성공 후 토큰 생성 및 반환
        Token token = jwtTokenProvider.createToken(newMember.getId(), request.email(), "ROLE_USER");

        // 리프레시 토큰을 데이터베이스에 저장
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity
                .builder()
                .email(request.email())
                .refreshToken(token.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshTokenEntity);

        return token;
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

        if (!request.equals(request)) {
            log.warn("[쿠키로 들어온 토큰과 DB의 토큰이 일치하지 않음.]");
            throw new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
        }

        Token token = jwtTokenProvider.createToken(memberId, email, role);
        findToken.updateRefreshToken(token.getRefreshToken());
        refreshTokenRepository.save(findToken);

        log.info("재발급 된 액세스 토큰: {}", token.getAccessToken());
        log.info("재발급 된 refresh 토큰: {}", token.getRefreshToken());
        return ReissueResponse.from(token.getAccessToken(),token.getRefreshToken());
    }

    @Transactional
    public void logout(LogoutRequest request) {
        String memberId = jwtTokenProvider.getEmail(request.refreshToken());
        RefreshTokenEntity existRefreshToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST));
        setBlackList(request.refreshToken());
        log.info("[로그아웃 된 리프레시 토큰 블랙리스트 처리]");
        refreshTokenRepository.delete(existRefreshToken);
        log.info("삭제 요청된 refreshToken: {}", request.refreshToken());
    }

    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordRequest request){

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

        return new ChangePasswordResponse(tempPassword);
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