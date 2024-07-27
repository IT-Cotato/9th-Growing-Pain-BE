package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
