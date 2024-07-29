package cotato.growingpain.post.repository;

import cotato.growingpain.post.domain.entity.PostSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSaveRepository extends JpaRepository<PostSave, Long> {

    boolean existsById(Long postSaveId);
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
    void deleteByMemberIdAndPostId(Long memberId, Long postId);
}