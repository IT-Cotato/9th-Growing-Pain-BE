package cotato.growingpain.log.repository;

import cotato.growingpain.log.domain.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
