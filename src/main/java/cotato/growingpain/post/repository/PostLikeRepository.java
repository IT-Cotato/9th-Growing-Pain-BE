package cotato.growingpain.post.repository;

import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByMemberAndPost(Member member, Post post);

    @Modifying
    @Query(value = "delete from PostLike p where p.post.id=:postId")
    void deleteByPostId(Long postId);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);
}