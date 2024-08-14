package cotato.growingpain.member.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.MemberProfileShowing;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.dto.request.AdditionalInfoRequest;
import cotato.growingpain.member.dto.request.UpdateDefaultInfoRequest;
import cotato.growingpain.member.dto.response.MemberInfoResponse;
import cotato.growingpain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateDefaultInfo(UpdateDefaultInfoRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new AppException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateDefaultInfo(request.field(), request.belong(), request.job());
        memberRepository.save(member);
    }

    @Transactional
    public void registerAdditionalInfo(AdditionalInfoRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new AppException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateAdditionalInfo(
                request.educationBackground(),
                request.skill(),
                request.activityHistory(),
                request.award(),
                request.languageScore(),
                request.career(),
                request.aboutMe()
        );
        memberRepository.save(member);
    }

    @Transactional
    public void updateProfileShowing(Long memberId, MemberProfileShowing memberProfileShowing) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateProfilePublic(memberProfileShowing);
        memberRepository.save(member);
    }

    @Transactional
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getMemberProfileShowing() == MemberProfileShowing.PUBLIC) {
            return MemberInfoResponse.fromMember(member);
        } else {
            return MemberInfoResponse.defaultInfoFromMember(member);
        }
    }
}