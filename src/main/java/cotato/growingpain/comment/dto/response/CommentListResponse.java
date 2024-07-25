package cotato.growingpain.comment.dto.response;

import cotato.growingpain.comment.domain.entity.Comment;
import java.util.List;

public record CommentListResponse(List<Comment> comments) {
}
