package cotato.growingpain.replycomment.repository;

import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.domain.entity.ReplyCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentLikeRepository extends JpaRepository<ReplyCommentLike, Long> {
    boolean existsByMemberAndReplyComment(Member member, ReplyComment replyComment);
}