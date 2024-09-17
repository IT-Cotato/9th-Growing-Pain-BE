package cotato.growingpain.post.repository;

import cotato.growingpain.post.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteAllByPostId(Long postId);
}
