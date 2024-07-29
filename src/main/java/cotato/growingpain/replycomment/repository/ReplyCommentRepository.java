package cotato.growingpain.replycomment.repository;

import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    List<ReplyComment> findByCommentId(Long commentId);

    @Modifying
    @Query("delete from ReplyComment r where r.comment.id = :commentId")
    void deleteAllByCommentId(Long commentId);

    Optional<ReplyComment> findByIdAndMemberId(Long replyCommentId, Long memberId);
}