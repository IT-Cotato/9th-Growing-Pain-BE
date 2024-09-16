package cotato.growingpain.comment.dto.response;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.post.dto.response.PostListResponse;
import cotato.growingpain.post.dto.response.PostResponse;
import java.util.List;

public record CommentListResponse(
        List<CommentResponse> commentList
) {
    public static CommentListResponse from(List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::from)
                .toList();
        return new CommentListResponse(commentResponses);
    }
}