package cotato.growingpain.post.repository;

import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByMemberAndPost(Member member, Post post);
}