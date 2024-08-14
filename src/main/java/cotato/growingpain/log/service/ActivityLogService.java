package cotato.growingpain.log.service;

import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.dto.ActivityLogDTO;
import cotato.growingpain.log.dto.request.ActivityLogRequestDTO;
import cotato.growingpain.log.repository.ActivityLogRepository;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
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
    public void createActivityLog(final ActivityLogRequestDTO activityLogRequestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));
        ActivityLog activityLog = activityLogRequestDTO.toEntity(member);
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public List<ActivityLog> retrieveActivityLogsByMemberId(Long memberId) {
        return activityLogRepository.findByMemberId(memberId);
    }

    @Transactional
    public ActivityLogDTO retrieveActivityLogById(Long activityLogId) {
        ActivityLog activityLog = activityLogRepository.findById(activityLogId)
                .orElseThrow(() -> new NoSuchElementException("ActivityLog not found with ID: " + activityLogId));

        return ActivityLogDTO.fromEntity(activityLog);
    }
}
