package cotato.growingpain.auth.controller;

import cotato.growingpain.auth.dto.request.JoinRequest;
import cotato.growingpain.security.jwt.dto.request.ReissueRequest;
import cotato.growingpain.security.jwt.dto.response.ReissueResponse;
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
    public ResponseEntity<?> joinAuth(@RequestBody @Valid JoinRequest request) {
        log.info("[회원 가입 컨트롤러]: {}", request.email());
        authService.createLoginInfo(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}