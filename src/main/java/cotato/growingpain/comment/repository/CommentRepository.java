package cotato.growingpain.comment.repository;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.dto.response.CommentResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new cotato.growingpain.comment.dto.response.CommentResponse(c.post.id, c.id, c.createdAt, c.modifiedAt, c.content, c.likeCount, c.isDeleted, c.member.id, c.member.profileImageUrl, c.member.name, c.member.field) FROM Comment c WHERE c.member.id = :memberId AND c.isDeleted = false")
    List<CommentResponse> findByMemberIdAndIsDeletedFalse(@Param("memberId") Long memberId);

    @Query("SELECT new cotato.growingpain.comment.dto.response.CommentResponse(c.post.id, c.id, c.createdAt, c.modifiedAt, c.content, c.likeCount, c.isDeleted, c.member.id, c.member.profileImageUrl, c.member.name, c.member.field) FROM Comment c WHERE c.post.id = :postId AND c.isDeleted = false")
    List<CommentResponse> findByPostIdAndIsDeletedFalse(@Param("postId") Long postId);

    List<Comment> findAllByPostIdAndIsDeletedFalse(Long postId);

    Optional<Comment> findAllByIdAndMemberIdAndIsDeletedFalse(Long commentId, Long memberId);
}