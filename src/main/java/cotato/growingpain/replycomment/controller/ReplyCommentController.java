package cotato.growingpain.replycomment.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.replycomment.dto.request.ReplyCommentRegisterRequest;
import cotato.growingpain.replycomment.service.ReplyCommentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "답글", description = "답글 또는 대댓글 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply-comment")
@Slf4j
public class ReplyCommentController {

    private final ReplyCommentService replyCommentService;

    @Operation(summary = "답글 등록", description = "답글 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerComment(@Valid @RequestBody ReplyCommentRegisterRequest request,
                                       @RequestParam Long postId,
                                       @RequestParam Long commentId,
                                       @AuthenticationPrincipal Long memberId) {
        log.info("답글 등록한 memberId: {}", memberId);
        log.info("답글이 등록된 postId: {}", postId);
        log.info("답글이 등록된 commentId: {}", commentId);
        replyCommentService.registerReplyComment(request, postId, commentId,memberId);
        return Response.createSuccessWithNoData("딥글 생성 완료");
    }
}
