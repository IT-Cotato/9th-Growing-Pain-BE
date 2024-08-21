package cotato.growingpain.replycomment.dto.response;

import java.util.List;

public record ReplyCommentListResponse(
        List<ReplyCommentResponse> replyCommentList
) {
}