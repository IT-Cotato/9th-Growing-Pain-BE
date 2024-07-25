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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth 관련된 api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입 및 로그인을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK )
    public Response<?> joinAuth(@RequestBody @Valid JoinRequest request) {
        log.info("[회원 가입 컨트롤러]: {}", request.email());
        Token token = authService.createLoginInfo(request);
        return Response.createSuccess("회원가입 완료",token);
    }

    @Operation(summary = "리이슈", description = "리이슈 및 토큰 재발급을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = ReissueResponse.class)))
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public Response<ReissueResponse> tokenRefresh(@RequestBody ReissueRequest request) {
        ReissueResponse reissueResponse = authService.tokenReissue(request);
        return Response.createSuccess("리이슈 완료", reissueResponse);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
        return Response.createSuccessWithNoData("로그아웃 성공");
    }

    @Operation(summary = "비밀번호 초기화", description = "비밀번호 찾기 및 초기화를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = ChangePasswordResponse.class)))
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public Response<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request){
        log.info("[비밀번호 초기화 컨트롤러]: {}", request.email());
        return Response.createSuccess("비밀번호 초기화 완료",authService.changePassword(request));
    }
}