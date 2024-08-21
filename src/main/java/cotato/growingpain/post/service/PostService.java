package cotato.growingpain.post.service;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.repository.CommentRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.PostCategory;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.dto.request.PostRequest;
import cotato.growingpain.post.repository.PostLikeRepository;
import cotato.growingpain.post.repository.PostRepository;
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
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Transactional
    public void registerPost(PostRequest request, Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        PostCategory parentCategory = request.category().getParent();
        postRepository.save(
                Post.of(member, request.title(), request.content(), request.imageUrl(), parentCategory,
                        request.category())
        );
    }

    public List<Post> getPostsByMemberId(Long memberId) {
        return postRepository.findByMemberIdAndIsDeletedFalse(memberId);
    }

    public List<Post> getPostsByCategory(PostCategory category){
        return postRepository.findByCategoryAndIsDeletedFalse(category);
    }

    public List<Post> getAllPostsByCategory() {
        return postRepository.findAllByIsDeletedFalse();
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) {
        Post post = findByPostIdAndMemberId(postId, memberId);

        if(post.isDeleted()) {
            throw new AppException(ErrorCode.ALREADY_DELETED);
        }

        List<Comment> comments = commentRepository.findCommentsByPostIdAndIsDeletedFalse(postId);
        for (Comment comment : comments) {
            replyCommentRepository.deleteAllByCommentId(comment.getId());
            commentRepository.delete(comment);
        }

        postLikeRepository.deleteByPostId(postId);

        post.deletePost();
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long postId, PostRequest request, Long memberId) {
        Post post = findByPostIdAndMemberId(postId, memberId);

        if (post.isDeleted()) {
            throw new AppException(ErrorCode.ALREADY_DELETED);
        }

        post.updatePost(request.title(), request.content(), request.imageUrl(), request.category());
        postRepository.save(post);
    }

    private Post findByPostIdAndMemberId(Long postId, Long memberId) {
        return postRepository.findByIdAndMemberIdAndIsDeletedFalse(postId, memberId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
    }
}