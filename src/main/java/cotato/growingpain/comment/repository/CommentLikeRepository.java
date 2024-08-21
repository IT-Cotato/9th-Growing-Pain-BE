package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.domain.entity.CommentLike;
import cotato.growingpain.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByMemberAndComment(Member member, Comment comment);

    @Modifying
    @Query(value = "delete from CommentLike c where c.comment.id=:commentId")
    void deleteByCommentId(Long commentId);

    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);
}