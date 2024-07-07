package cotato.growingpain.auth.controller;

import cotato.growingpain.auth.dto.request.JoinRequest;
import cotato.growingpain.auth.service.AuthService;
import cotato.growingpain.security.jwt.Token;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> joinAuth(@RequestBody @Valid JoinRequest request) {
        log.info("[회원 가입 컨트롤러]: {}", request.email());
        Token token = authService.createLoginInfo(request);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token.getAccessToken());
        response.put("refreshToken", token.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}