package cotato.growingpain.replycomment.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.replycomment.service.ReplyCommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "답글 좋아요", description = "답글 좋아요 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply-comment/{reply-comment-id}/likes")
@Slf4j
public class ReplyCommentLikeController {

    private final ReplyCommentLikeService replyCommentLikeService;

    @Operation(summary = "답글 좋아요 등록", description = "답글 좋아요 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerLike(@PathVariable("reply-comment-id") Long replyCommentId,
                                    @AuthenticationPrincipal Long memberId) {

        replyCommentLikeService.registerLike(replyCommentId, memberId);
        log.info("답글 {} 좋아요 한 memberId: {}", replyCommentId, memberId);
        return Response.createSuccessWithNoData("댓글 좋아요 등록 완료");
    }

}
