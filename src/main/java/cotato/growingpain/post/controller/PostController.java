package cotato.growingpain.post.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.dto.request.PostRequest;
import cotato.growingpain.post.dto.response.PostListResponse;
import cotato.growingpain.post.service.PostService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글", description = "게시글 또는 포스트 관련된 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Slf4j
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록", description = "게시글 등록을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerPost(@Valid @RequestBody PostRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        log.info("게시글 등록한 memberId: {}", memberId);
        postService.registerPost(request, memberId);
        return Response.createSuccessWithNoData("포스트 생성 완료");
    }

    @Operation(summary = "게시글 목록 조회", description = "사용자가 등록한 게시글의 목록 전체 조회를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = PostListResponse.class)))
    @GetMapping("/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<PostListResponse> getPostsByMemberId(@AuthenticationPrincipal Long memberId) {
        List<Post> posts = postService.getPostsByMemberId(memberId);
        log.info("{}가 등록한 게시글 목록", memberId);
        PostListResponse postListResponse = new PostListResponse(posts);
        return Response.createSuccess("사용자의 게시글 목록 조회 완료", postListResponse);
    }

    @Operation(summary = "카테고리별 게시글 목록 조회", description = "카테고리별로 게시글 목록 조회 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = PostListResponse.class)))
    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public Response<PostListResponse> getPostsByCategory(@RequestParam PostCategory category) {
        List<Post> posts;
        log.info("카테고리별 게시글 목록 요청: {}", category);
        if (category == PostCategory.ALL) {
            posts = postService.getAllPosts();
        } else {
            posts = postService.getPostsByCategory(category);
        }
        PostListResponse postListResponse = new PostListResponse(posts);
        return Response.createSuccess(category == PostCategory.ALL ? "전체 게시글 조회 완료" : "카테고리별 게시글 목록 조회 완료", postListResponse);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제를 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @DeleteMapping("/{postId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> deletePost(@PathVariable Long postId,
                                  @AuthenticationPrincipal Long memberId) {
        log.info("게시글 삭제한 memberId: {}", memberId);
        postService.deletePost(postId, memberId);
        return Response.createSuccessWithNoData("포스트 삭제 완료");
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/{postId}/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<?> registerPost(@PathVariable Long postId,
                                    @RequestBody PostRequest request,
                                    @AuthenticationPrincipal Long memberId) {
        log.info("게시글 {} 수정한 memberId: {}", postId, memberId);
        postService.updatePost(postId, request,memberId);
        return Response.createSuccessWithNoData("포스트 수정 완료");
    }
}