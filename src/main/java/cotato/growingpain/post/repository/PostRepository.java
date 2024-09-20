package cotato.growingpain.post.repository;

import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByMemberIdAndIsDeletedFalse(Long memberId);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND (p.parentCategory = :category OR p.subCategory = :category)")
    List<Post> findAllByCategoryAndIsDeletedFalse(@Param("category") PostCategory category);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = false")
    List<Post> findAllByIsDeletedFalse();

    Optional<Post> findAllByIdAndMemberIdAndIsDeletedFalse(Long postId, Long memberId);
}