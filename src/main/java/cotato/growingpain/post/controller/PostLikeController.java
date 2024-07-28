package cotato.growingpain.post.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.post.service.PostLikeService;
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

@Tag(name = "게시글 좋아요", description = "게시글 좋아요 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{post-id}/likes")
@Slf4j
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요 등록", description = "게시글 좋아요 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerLike(@PathVariable("post-id") Long postId,
                                    @AuthenticationPrincipal Long memberId) {

        postLikeService.registerLike(postId, memberId);
        log.info("게시글 {} 좋아요 한 memberId: {}", postId, memberId);
        return Response.createSuccessWithNoData("포스트 좋아요 등록 완료");
    }

    @Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요 취소를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @DeleteMapping("/{post-like-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> deleteLike(@PathVariable("post-id") Long postId,
                                  @PathVariable("post-like-id") Long postLikeId,
                                  @AuthenticationPrincipal Long memberId) {

        postLikeService.deleteLike(postId, postLikeId, memberId);
        return Response.createSuccessWithNoData("포스트 좋아요 취소 완료");
    }
}