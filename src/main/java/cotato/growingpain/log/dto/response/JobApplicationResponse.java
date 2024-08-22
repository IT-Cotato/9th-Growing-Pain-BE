package cotato.growingpain.log.dto.response;

import cotato.growingpain.log.domain.entity.JobApplication;

public record JobApplicationResponse(

        Long jobPostId,
        Long jobApplicationId,
        String companyName,
        String applicationType,
        String applicationCloseDate
) {
    public JobApplicationResponse(JobApplication jobApplication) {
        this(
                jobApplication.getJobPost().getId(),
                jobApplication.getId(),
                jobApplication.getJobPost().getCompanyName(),
                jobApplication.getApplicationType().name(),
                jobApplication.getApplicationCloseDate()
        );
    }
}
