package cotato.growingpain.log.domain.dto;

import cotato.growingpain.log.domain.ApplicationType;
import cotato.growingpain.log.domain.Result;
import cotato.growingpain.log.domain.entity.ApplicationDetail;
import cotato.growingpain.log.domain.entity.JobApplication;
import cotato.growingpain.log.domain.entity.JobPost;
import cotato.growingpain.member.domain.entity.Member;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record JobApplicationRequestDTO(
        String applicationType,
        String place,
        String result,
        boolean submissionStatus,
        String applicationStartDate,
        String applicationCloseDate,
        Long memberId,
        Long jobPostId,
        List<ApplicationDetailRequestDTO> applicationDetails
) {
    public JobApplication toEntity(Member member, JobPost jobPost) {
        JobApplication jobApplication = JobApplication.builder()
                .applicationType(ApplicationType.valueOf(this.applicationType))
                .place(this.place)
                .result(Result.valueOf(this.result))
                .submissionStatus(this.submissionStatus)
                .applicationStartDate(this.applicationStartDate)
                .applicationCloseDate(this.applicationCloseDate)
                .member(member)
                .jobPost(jobPost)
                .build();

        log.info("jobApplication {}", jobApplication);

        List<ApplicationDetail> details = this.applicationDetails.stream()
                .map(ApplicationDetailRequestDTO -> ApplicationDetailRequestDTO.toEntity(jobApplication))
                .toList();
        details.forEach(jobApplication::addApplicationDetail);

        return jobApplication;
    }
}
