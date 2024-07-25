package cotato.growingpain.replycomment.dto.response;

import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import java.util.List;

public record ReplyCommentListResponse(List<ReplyComment> replyComments) {
}