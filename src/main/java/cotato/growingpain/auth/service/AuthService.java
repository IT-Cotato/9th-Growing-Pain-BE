package cotato.growingpain.auth.service;

import cotato.growingpain.auth.dto.request.JoinRequest;
import cotato.growingpain.security.jwt.dto.request.ReissueRequest;
import cotato.growingpain.security.jwt.dto.response.ReissueResponse;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.security.RefreshTokenRepository;
import cotato.growingpain.security.jwt.RefreshToken;
import cotato.growingpain.security.jwt.Token;
import cotato.growingpain.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Token createLoginInfo(JoinRequest request) {

        validateService.checkPasswordPattern(request.password());
        validateService.checkDuplicateEmail(request.email());
        validateService.checkDuplicateNickName(request.name());

        log.info("[회원 가입 서비스]: {}", request.email());

        Member newMember = Member.builder()
                .password(bCryptPasswordEncoder.encode(request.password()))
                .email(request.email())
                .name(request.name())
                .field(request.field())
                .belong(request.belong())
                .build();
        memberRepository.save(newMember);

        // 회원가입 성공 후 토큰 생성 및 반환
        return tokenProvider.createToken(request.email(), "ROLE_USER");
    }

    @Transactional
    public ReissueResponse tokenReissue(ReissueRequest request) {

        String memberId = tokenProvider.getEmail(request.refreshToken());
        String role = tokenProvider.getRole(request.refreshToken());

        log.info("재발급 요청된 이메일: {}", memberId);

        // 데이터베이스에서 이메일 확인 로그 추가
        RefreshToken findToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        log.info("DB에서 찾은 리프레시 토큰: {}", request);

        if (tokenProvider.isExpired(request.refreshToken())) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        if (!request.equals(request)) {
            log.warn("[쿠키로 들어온 토큰과 DB의 토큰이 일치하지 않음.]");
            throw new AppException(ErrorCode.REFRESH_TOKEN_NOT_EXIST);
        }

        Token token = tokenProvider.createToken(memberId, role);
        findToken.updateRefreshToken(token.getRefreshToken());
        refreshTokenRepository.save(findToken);
        log.info("재발급 된 액세스 토큰: {}", token.getRefreshToken());
        return ReissueResponse.from(token.getAccessToken(), token.getRefreshToken());
    }

}