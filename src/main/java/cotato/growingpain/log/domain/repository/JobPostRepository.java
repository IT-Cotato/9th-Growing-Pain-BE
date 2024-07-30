package cotato.growingpain.log.domain.repository;

import cotato.growingpain.log.domain.entity.JobPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByMemberId(Long memberId);
}