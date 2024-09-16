package cotato.growingpain.replycomment.repository;

import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.domain.entity.ReplyCommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyCommentLikeRepository extends JpaRepository<ReplyCommentLike, Long> {
    boolean existsByMemberAndReplyComment(Member member, ReplyComment replyComment);

    @Modifying
    @Query(value = "delete from ReplyCommentLike r where r.replyComment.id=:replyCommentId")
    void deleteByReplyCommentId(Long replyCommentId);


    Optional<ReplyCommentLike> findAllByMemberAndReplyComment(Member member, ReplyComment replyComment);
}