package cotato.growingpain.comment.service;

import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.domain.entity.CommentLike;
import cotato.growingpain.comment.repository.CommentLikeRepository;
import cotato.growingpain.comment.repository.CommentRepository;
import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;

    public void registerLike(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);

        if (commentLikeRepository.existsByMemberAndComment(member, comment)) {
            log.info("이미 좋아요를 누른 댓글입니다: commentId={}, memberId={}", commentId, memberId);
            throw new AppException(ErrorCode.ALREADY_LIKED);
        }

        CommentLike commentLike = CommentLike.of(member, comment);
        commentLike.increaseCommentLikeCount();

        commentLikeRepository.save(commentLike);
    }

    public void deleteLike(Long commentId, Long commentLikeId, Long memberId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_LIKE_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);

        commentLike.decreaseCommentLikeCount(member, comment);
        commentLikeRepository.save(commentLike);
    }
}
