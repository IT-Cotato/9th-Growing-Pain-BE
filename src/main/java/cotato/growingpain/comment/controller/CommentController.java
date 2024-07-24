package cotato.growingpain.comment.controller;

import cotato.growingpain.comment.dto.request.CommentRegisterRequest;
import cotato.growingpain.comment.service.CommentService;
import cotato.growingpain.common.Response;
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
        Long commentId = commentService.registerComment(request, memberId, postId);
        return Response.createSuccess("댓글 생성 완료", commentId);
    }
}
