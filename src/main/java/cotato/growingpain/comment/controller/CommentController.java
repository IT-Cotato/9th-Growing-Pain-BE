package cotato.growingpain.comment.controller;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.dto.request.CommentRegisterRequest;
import cotato.growingpain.comment.dto.response.CommentListResponse;
import cotato.growingpain.comment.service.CommentService;
import cotato.growingpain.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글", description = "댓글 또는 코멘트 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록", description = "댓글 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerComment(@Valid @RequestBody CommentRegisterRequest request,
                                       @RequestParam Long postId,
                                       @AuthenticationPrincipal Long memberId) {
        log.info("댓글 등록한 memberId: {}", memberId);
        log.info("댓글이 등록된 postId: {}", postId);
        commentService.registerComment(request, memberId, postId);
        return Response.createSuccessWithNoData("댓글 생성 완료");
    }

    @Operation(summary = "사용자별 댓글 목록 조회", description = "사용자별 댓글 목록 조회를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = CommentListResponse.class)))
    @GetMapping("/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<CommentListResponse> getCommentsByMemberId(@AuthenticationPrincipal Long memberId) {
        List<Comment> comments = commentService.getCommentsByMemberId(memberId);
        log.info("{}가 등록한 게시글 목록", memberId);
        CommentListResponse commentListResponse = new CommentListResponse(comments);
        return Response.createSuccess("사용자 댓글 목록 조회 완료", commentListResponse);
    }

    @Operation(summary = "포스트별 댓글 목록 조회", description = "한 포스트내의 댓글 목록 조회를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = CommentListResponse.class)))
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Response<CommentListResponse> getCommentsByPostId(@RequestParam Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        CommentListResponse commentListResponse = new CommentListResponse(comments);
        return Response.createSuccess("포스트별 댓글 목록 조회 완료", commentListResponse);
    }
}