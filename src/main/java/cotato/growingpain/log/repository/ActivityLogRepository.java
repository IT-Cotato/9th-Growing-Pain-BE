package cotato.growingpain.log.repository;

import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.dto.request.ActivityLogRequestDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLogRequestDTO> findByMemberId(Long memberId);

    Optional<ActivityLog> findByMemberIdAndId(Long activityLogId, Long memberId);

    Optional<ActivityLog> findById(Long activityLogId);
}
