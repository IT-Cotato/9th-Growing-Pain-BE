package cotato.growingpain.replycomment.service;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.repository.CommentRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.repository.PostRepository;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.dto.request.ReplyCommentRegisterRequest;
import cotato.growingpain.replycomment.dto.response.ReplyCommentListResponse;
import cotato.growingpain.replycomment.dto.response.ReplyCommentResponse;
import cotato.growingpain.replycomment.repository.ReplyCommentLikeRepository;
import cotato.growingpain.replycomment.repository.ReplyCommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyCommentService {

    private final ReplyCommentRepository replyCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyCommentLikeRepository replyCommentLikeRepository;

    @Transactional
    public void registerReplyComment(ReplyCommentRegisterRequest request, Long postId, Long commentId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        replyCommentRepository.save(
                ReplyComment.of(member, post, comment, request.content())
        );
    }

    @Transactional(readOnly = true)
    public ReplyCommentListResponse getReplyCommentsByCommentId(Long commentId) {
        List<ReplyCommentResponse> replyCommentList = replyCommentRepository.findByCommentId(commentId);
        return new ReplyCommentListResponse(replyCommentList);
    }

    @Transactional
    public void deleteReplyComment(Long replyCommentId, Long memberId) {
        ReplyComment replyComment = replyCommentRepository.findAllByIdAndMemberId(replyCommentId, memberId)
                .orElseThrow(() -> new AppException(ErrorCode.REPLY_COMMENT_NOT_FOUND));


        replyCommentLikeRepository.deleteByReplyCommentId(replyCommentId);
        replyCommentRepository.delete(replyComment);
    }
}
