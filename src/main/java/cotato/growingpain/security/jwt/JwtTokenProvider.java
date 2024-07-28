package cotato.growingpain.security.jwt;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    String secretKey;

    @Value("${jwt.access.expiration}")
    Long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    Long refreshExpiration;

    public boolean validateToken(String accessToken) {
        log.info("Validating access token: {}", accessToken);
        if (accessToken.isEmpty()) {
            throw new AppException(ErrorCode.JWT_NOT_EXISTS);
        }
        return isExpired(accessToken);
    }

    public boolean isExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    public Long getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberId", Long.class);
    }

    public Token createToken(Long memberId, String email, String authority) {
        return Token.builder()
                .accessToken(createAccessToken(memberId, email,authority))
                .refreshToken(createRefreshToken(memberId, email,authority))
                .build();
    }

    private String createAccessToken(Long memberId, String email,String authority) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("email", email);
        claims.put("role",authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private String createRefreshToken(Long memberId, String email,String authority) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("email", email);
        claims.put("role",authority);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Long getExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getExpiration().getTime() - new Date().getTime();
    }
}