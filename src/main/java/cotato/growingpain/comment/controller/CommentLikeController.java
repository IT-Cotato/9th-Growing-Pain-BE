package cotato.growingpain.comment.controller;

import cotato.growingpain.comment.service.CommentLikeService;
import cotato.growingpain.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 좋아요", description = "댓글 좋아요 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/{commentId}/likes")
@Slf4j
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 좋아요 등록", description = "댓글 좋아요 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> registerLike(@PathVariable Long commentId,
                                    @AuthenticationPrincipal Long memberId) {

        commentLikeService.registerLike(commentId, memberId);
        log.info("댓글 {} 좋아요 한 memberId: {}", commentId, memberId);
        return Response.createSuccessWithNoData("댓글 좋아요 등록 완료");
    }

    @Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요 취소를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> deleteLike(@PathVariable Long commentId,
                                  @AuthenticationPrincipal Long memberId) {

        commentLikeService.deleteLike(commentId, memberId);
        return Response.createSuccessWithNoData("댓글 좋아요 취소 완료");
    }
}