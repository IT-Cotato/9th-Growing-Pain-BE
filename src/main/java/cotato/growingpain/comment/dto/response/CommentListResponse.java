package cotato.growingpain.comment.dto.response;

import java.util.List;

public record CommentListResponse(
        List<CommentResponse> commentList
) {
}