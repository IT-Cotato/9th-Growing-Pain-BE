package cotato.growingpain.log.domain.repository;

import cotato.growingpain.log.domain.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
