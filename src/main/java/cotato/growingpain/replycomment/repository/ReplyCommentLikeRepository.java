package cotato.growingpain.replycomment.repository;

import cotato.growingpain.replycomment.domain.entity.ReplyCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentLikeRepository extends JpaRepository<ReplyCommentLike, Long> {
}
