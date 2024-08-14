package cotato.growingpain.log.service;

import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.dto.ActivityLogDTO;
import cotato.growingpain.log.repository.ActivityLogRepository;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final MemberRepository memberRepository;
    private final ActivityLogRepository activityLogRepository;

    @Transactional
    public void createActivityLog(final ActivityLogDTO activityLogDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));
        ActivityLog activityLog = activityLogDto.toEntity(member);
        activityLogRepository.save(activityLog);
    }

}
