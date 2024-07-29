package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMemberId(Long memberId);
    List<Comment> findByPostId(Long postId);

    Optional<Comment> findByIdAndMemberId(Long commentId, Long memberId);
}