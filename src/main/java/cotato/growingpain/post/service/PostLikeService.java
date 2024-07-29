package cotato.growingpain.post.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostLike;
import cotato.growingpain.post.repository.PostLikeRepository;
import cotato.growingpain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    public void registerLike(Long postId, Long memberId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);
        post.validatePostLike(member);

        if (postLikeRepository.existsByMemberAndPost(member, post)) {
            log.info("이미 좋아요를 누른 포스트입니다: postId={}, memberId={}", postId, memberId);
            throw new AppException(ErrorCode.ALREADY_LIKED);
        }

        PostLike postLike = PostLike.of(member, post);
        postLike.increasePostLikeCount();

        postLikeRepository.save(postLike);
    }

    public void deleteLike(Long postId, Long postLikeId, Long memberId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        PostLike postLike = postLikeRepository.findById(postLikeId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_LIKE_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);

        postLike.decreasePostLikeCount(member, post);
        postLikeRepository.save(postLike);
    }
}