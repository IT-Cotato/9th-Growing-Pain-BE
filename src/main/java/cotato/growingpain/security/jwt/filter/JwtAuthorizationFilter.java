package cotato.growingpain.security.jwt.filter;

import cotato.growingpain.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
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

    private void getAuthentication(String accessToken) {
        log.info("getAuthentication");
        Long memberId = jwtTokenProvider.getMemberId(accessToken);
        String email = jwtTokenProvider.getEmail(accessToken);
        String role = jwtTokenProvider.getRole(accessToken);
        log.info("Extracted memberId: {}, email: {}, role: {}", memberId, email, role);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(memberId, "",
                List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("Authentication set for memberId: {}", memberId);
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return getBearer(header);
        } else {
            return null;
        }
    }

    public String getBearer(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }
}