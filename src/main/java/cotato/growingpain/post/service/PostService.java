package cotato.growingpain.post.service;

import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.dto.request.PostRegisterRequest;
import cotato.growingpain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long registerPost(PostRegisterRequest request, Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        PostCategory parentCategory = request.category().getParent();
        return postRepository.save(
                Post.of(member, request.title(), request.content(), request.imageUrl(), parentCategory, request.category())
        ).getId();
    }

    public List<Post> getPostsByMemberId(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public List<Post> getPostsByCategory(PostCategory category){
        if (category.getParent() == null) {
            return postRepository.findByParentCategory(category);
        } else {
            return postRepository.findBySubCategory(category);
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}