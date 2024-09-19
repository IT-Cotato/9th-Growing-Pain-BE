package cotato.growingpain.post.repository;

import cotato.growingpain.post.domain.entity.PostImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteAllByPostId(Long postId);

    List<PostImage> findAllByPostId(Long postId);
}
