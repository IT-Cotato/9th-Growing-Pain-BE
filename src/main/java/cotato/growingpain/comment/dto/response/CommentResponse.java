package cotato.growingpain.comment.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long postId,
        Long memberId,
        String profileImageUrl,
        LocalDateTime createdAt,
        String content
) {
}