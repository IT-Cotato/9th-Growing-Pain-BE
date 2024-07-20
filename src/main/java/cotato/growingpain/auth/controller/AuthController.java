package cotato.growingpain.auth.controller;

import cotato.growingpain.auth.dto.request.ChangePasswordRequest;
import cotato.growingpain.auth.dto.request.JoinRequest;
import cotato.growingpain.auth.dto.request.LogoutRequest;
import cotato.growingpain.auth.dto.response.ChangePasswordResponse;
import cotato.growingpain.common.Response;
import cotato.growingpain.security.jwt.dto.request.ReissueRequest;
import cotato.growingpain.security.jwt.dto.response.ReissueResponse;
import cotato.growingpain.auth.service.AuthService;
import cotato.growingpain.security.jwt.Token;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Response<?> joinAuth(@RequestBody @Valid JoinRequest request) {
        log.info("[회원 가입 컨트롤러]: {}", request.email());
        Token token = authService.createLoginInfo(request);
        return Response.createSuccess("회원가입 완료",token);
    }

    @PostMapping("/reissue")
    public Response<ReissueResponse> tokenRefresh(@RequestBody ReissueRequest request) {
        ReissueResponse reissueResponse = authService.tokenReissue(request);
        return Response.createSuccess("리이슈 완료", reissueResponse);
    }

    @PostMapping("/logout")
    public Response<?> logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
        return Response.createSuccessWithNoData("로그아웃 성공");
    }

    @PostMapping("/change-password")
    public Response<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request){
        log.info("[비밀번호 초기화 컨트롤러]: {}", request.email());
        return Response.createSuccess("비밀번호 초기화 완료",authService.changePassword(request));
    }
}