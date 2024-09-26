package cotato.growingpain.comment.service;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.dto.request.CommentRegisterRequest;
import cotato.growingpain.comment.dto.response.CommentListResponse;
import cotato.growingpain.comment.dto.response.CommentResponse;
import cotato.growingpain.comment.repository.CommentLikeRepository;
import cotato.growingpain.comment.repository.CommentRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.repository.PostRepository;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.repository.ReplyCommentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Transactional
    public void registerComment(CommentRegisterRequest request, Long memberId, Long postId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        commentRepository.save(
                Comment.of(member, post, request.content())
        );
    }

    @Transactional(readOnly = true)
    public CommentListResponse getCommentsByMemberId(Long memberId) {
        List<CommentResponse> commentList = commentRepository.findByMemberIdAndIsDeletedFalse(memberId);
        return new CommentListResponse(commentList);
    }

    @Transactional(readOnly = true)
    public CommentListResponse getCommentsByPostId(Long postId) {
        List<CommentResponse> commentList = commentRepository.findByPostIdAndIsDeletedFalse(postId);
        return new CommentListResponse(commentList);
    }

    @Transactional(readOnly = true)
    public CommentListResponse getAllPostsAndCommentsByMemberId(Long memberId) {
        // 사용자가 작성한 모든 포스트 조회
        List<Post> posts = postRepository.findAllByMemberId(memberId);
        List<CommentResponse> commentList = new ArrayList<>();

        // 각 포스트의 댓글 조회
        for (Post post : posts) {
            List<CommentResponse> comments = commentRepository.findByPostIdAndIsDeletedFalse(post.getId());
            commentList.addAll(comments);
        }
        return new CommentListResponse(commentList);
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findAllByIdAndMemberIdAndIsDeletedFalse(commentId, memberId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.isDeleted()) {
            throw new AppException(ErrorCode.ALREADY_DELETED);
        }

        List<ReplyComment> replyComments = replyCommentRepository.findReplyCommentByCommentId(commentId);
        replyCommentRepository.deleteAll(replyComments);

        commentLikeRepository.deleteByCommentId(commentId);

        comment.deleteComment();
        commentRepository.save(comment);
    }
}