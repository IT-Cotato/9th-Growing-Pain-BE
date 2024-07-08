package cotato.growingpain.security.jwt.filter;

import cotato.growingpain.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveAccessToken(request);
        log.info("액세스 토큰 반환 완료: {}",accessToken);
        if (accessToken != null && !accessToken.isBlank()) {
            if (!jwtTokenProvider.validateToken(accessToken)) {
                getAuthentication(accessToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        log.info("setAuthentication");
        String email = tokenProvider.getEmail(accessToken);
        String role = tokenProvider.getRole(accessToken);
    private void getAuthentication(String accessToken) {
        log.info("getAuthentication");
        String memberId = jwtTokenProvider.getMemberId(accessToken);
        String role = jwtTokenProvider.getRole(accessToken);
        log.info("Member Role: {}", role);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(memberId, "",
                List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/v1/api/auth") || path.startsWith("/login");
    }
}