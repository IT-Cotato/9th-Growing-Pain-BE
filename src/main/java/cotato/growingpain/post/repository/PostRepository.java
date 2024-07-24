package cotato.growingpain.post.repository;

import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);
    List<Post> findByParentCategory(PostCategory parentCategory);
    List<Post> findBySubCategory(PostCategory subCategory);
}