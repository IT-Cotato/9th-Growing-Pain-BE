package cotato.growingpain.member.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.member.dto.request.UpdateDefaultInfoRequest;
import cotato.growingpain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지", description = "마이페이지 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "기본 정보 수정", description = "정보 업데이트 중 기본 정보 수정을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/update-default-info")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> registerPost(@Valid @RequestBody UpdateDefaultInfoRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        log.info("기본 정보를 수정한 memberId: {}", memberId);
        memberService.updateDefaultInfo(request, memberId);
        return Response.createSuccessWithNoData("[마이페이지] 기본 정보 수정 완료");
    }
}