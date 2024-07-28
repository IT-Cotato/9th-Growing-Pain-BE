package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.domain.entity.CommentLike;
import cotato.growingpain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByMemberAndComment(Member member, Comment comment);
}