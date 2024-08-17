package cotato.growingpain.post.repository;

import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);

    @Query("SELECT p FROM Post p WHERE p.parentCategory = :category OR p.subCategory = :category")
    List<Post> findByCategory(@Param("category") PostCategory category);

    Optional<Post> findByIdAndMemberId(Long postId, Long memberId);
}