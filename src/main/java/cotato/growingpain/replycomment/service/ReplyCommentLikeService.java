package cotato.growingpain.replycomment.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.domain.entity.ReplyCommentLike;
import cotato.growingpain.replycomment.repository.ReplyCommentLikeRepository;
import cotato.growingpain.replycomment.repository.ReplyCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyCommentLikeService {

    private final ReplyCommentRepository replyCommentRepository;
    private final ReplyCommentLikeRepository replyCommentLikeRepository;
    private final MemberRepository memberRepository;

    public void registerLike(Long replyCommentId, Long memberId) {
        ReplyComment replyComment = replyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new AppException(ErrorCode.REPLY_COMMENT_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);
        replyComment.validateReplyCommentLike(member);

        if (replyCommentLikeRepository.existsByMemberAndReplyComment(member, replyComment)) {
            log.info("이미 좋아요를 누른 답글입니다: replyCommentId={}, memberId={}", replyCommentId, memberId);
            throw new AppException(ErrorCode.ALREADY_LIKED);
        }

        ReplyCommentLike replyCommentLike = ReplyCommentLike.of(member, replyComment);
        replyCommentLike.increaseReplyCommentLikeCount();

        replyCommentLikeRepository.save(replyCommentLike);
    }

    public void deleteLike(Long replyCommentId, Long replyCommentLikeId, Long memberId) {

        ReplyComment replyComment = replyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new AppException(ErrorCode.REPLY_COMMENT_NOT_FOUND));
        ReplyCommentLike replyCommentLike = replyCommentLikeRepository.findById(replyCommentLikeId)
                .orElseThrow(() -> new AppException(ErrorCode.REPLY_COMMENT_LIKE_NOT_FOUND));
        Member member = memberRepository.getReferenceById(memberId);

        replyCommentLike.decreaseReplyCommentLikeCount(member, replyComment);
        replyCommentLikeRepository.save(replyCommentLike);
    }
}
