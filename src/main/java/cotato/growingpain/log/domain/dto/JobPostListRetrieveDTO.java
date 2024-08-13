package cotato.growingpain.log.domain.dto;

import cotato.growingpain.log.domain.entity.JobPost;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record JobPostListRetrieveDTO(
        String companyName,
        String jobPart,
        List<JobApplicationRetrieveDTO> jobApplications
) {
    public static JobPostListRetrieveDTO fromEntity(JobPost jobPost) {
        List<JobApplicationRetrieveDTO> jobApplicationList = jobPost.getJobApplications().stream()
                .map(JobApplicationRetrieveDTO::fromEntity)
                .toList();

        return JobPostListRetrieveDTO.builder()
                .companyName(jobPost.getCompanyName())
                .jobPart(jobPost.getJobPart())
                .jobApplications(jobApplicationList)
                .build();
    }
}
