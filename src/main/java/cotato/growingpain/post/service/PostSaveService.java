package cotato.growingpain.post.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostSave;
import cotato.growingpain.post.repository.PostRepository;
import cotato.growingpain.post.repository.PostSaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSaveService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostSaveRepository postSaveRepository;

    @Transactional
    public void savePost(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (postSaveRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new AppException(ErrorCode.ALREADY_SAVED);
        }

        PostSave postSave = PostSave.createPostSave(member, post);
        postSaveRepository.save(postSave);
    }

    @Transactional
    public void deleteSavePost(Long postId, Long postSaveId, Long memberId) {

        if (!postSaveRepository.existsById(postSaveId)) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        postSaveRepository.deleteByMemberIdAndPostId(memberId, postId);
    }
}