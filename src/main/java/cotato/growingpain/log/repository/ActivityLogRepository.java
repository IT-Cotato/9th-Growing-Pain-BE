package cotato.growingpain.log.repository;

import cotato.growingpain.log.domain.entity.ActivityLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByMemberId(Long memberId);
}
