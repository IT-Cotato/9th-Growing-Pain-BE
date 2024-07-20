package cotato.growingpain.log.domain.repository;

import cotato.growingpain.log.domain.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
