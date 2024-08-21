package cotato.growingpain.replycomment.repository;

import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.dto.response.ReplyCommentResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query("SELECT new cotato.growingpain.replycomment.dto.response.ReplyCommentResponse(r.comment.id, r.id, r.createdAt, r.modifiedAt, r.content, r.likeCount, r.isDeleted, r.member.id, r.member.profileImageUrl, r.member.name, r.member.field) FROM ReplyComment r WHERE r.comment.id = :commentId")
    List<ReplyCommentResponse> findByCommentId(Long commentId);

    List<ReplyComment> findReplyCommentByCommentId(Long commentId);

    @Modifying
    @Query("delete from ReplyComment r where r.comment.id = :commentId")
    void deleteAllByCommentId(Long commentId);

    Optional<ReplyComment> findByIdAndMemberId(Long replyCommentId, Long memberId);
}