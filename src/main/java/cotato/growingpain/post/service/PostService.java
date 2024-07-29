package cotato.growingpain.post.service;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.repository.CommentRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostSave;
import cotato.growingpain.post.dto.request.PostRegisterRequest;
import cotato.growingpain.post.repository.PostLikeRepository;
import cotato.growingpain.post.repository.PostRepository;
import cotato.growingpain.post.repository.PostSaveRepository;
import cotato.growingpain.replycomment.repository.ReplyCommentRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final PostSaveRepository postSaveRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Transactional
    public void registerPost(PostRegisterRequest request, Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        PostCategory parentCategory = request.category().getParent();
        postRepository.save(
                Post.of(member, request.title(), request.content(), request.imageUrl(), parentCategory,
                        request.category())
        );
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

    @Transactional
    public void deletePost(Long postId, Long memberId) {
        Post post = postRepository.findByIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if(post.isDeleted()) {
            throw new AppException(ErrorCode.ALREADY_DELETED);
        }

        List<Comment> comments = commentRepository.findByPostId(postId);
        for (Comment comment : comments) {
            replyCommentRepository.deleteAllByCommentId(comment.getId());
            commentRepository.delete(comment);
        }

        postLikeRepository.deleteByPostId(postId);

        post.deletePost();
        postRepository.save(post);
    }

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
    public void deleteSavePost(Long postId, Long memberId) {
        if (!postSaveRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        postSaveRepository.deleteByMemberIdAndPostId(memberId, postId);
    }
}