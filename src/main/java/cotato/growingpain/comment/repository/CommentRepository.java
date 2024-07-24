package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMemberId(Long memberId);
}