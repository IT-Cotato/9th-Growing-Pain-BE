package cotato.growingpain.log.dto;

import cotato.growingpain.log.domain.entity.JobPost;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record JobPostRetrieveDTO(
        Long jobPostId,
        String companyName,
        String jobPart,
        List<JobApplicationRetrieveDTO> jobApplications
) {
    public static JobPostRetrieveDTO fromEntity(JobPost jobPost) {
        List<JobApplicationRetrieveDTO> jobApplication = jobPost.getJobApplications().stream()
                .map(JobApplicationRetrieveDTO::fromEntity)
                .toList();

        return JobPostRetrieveDTO.builder()
                .jobPostId(jobPost.getId())
                .companyName(jobPost.getCompanyName())
                .jobPart(jobPost.getJobPart())
                .jobApplications(jobApplication)
                .build();
    }

}
