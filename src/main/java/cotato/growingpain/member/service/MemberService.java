package cotato.growingpain.member.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.dto.request.UpdateDefaultInfoRequest;
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

        member.updateMemberInfo(request.name(), request.field(), request.belong(), request.job());
        memberRepository.save(member);
    }
}