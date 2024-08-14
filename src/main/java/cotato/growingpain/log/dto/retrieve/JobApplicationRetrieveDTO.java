package cotato.growingpain.log.dto.retrieve;

import cotato.growingpain.log.domain.entity.JobApplication;
import cotato.growingpain.log.dto.ApplicationDetailDTO;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record JobApplicationRetrieveDTO(
        Long id,
        String applicationType,
        String place,
        String result,
        String submissionStatus,
        String resultStatus,
        String applicationStartDate,
        String applicationCloseDate,
        Long memberId,
        Long jobPostId,
        List<ApplicationDetailDTO> applicationDetails
) {
    public static JobApplicationRetrieveDTO fromEntity(JobApplication jobApplication) {
        List<ApplicationDetailDTO> applicationDetailList = jobApplication.getApplicationDetails().stream()
                .map(ApplicationDetailDTO::fromEntity)
                .toList();

        return JobApplicationRetrieveDTO.builder()
                .id(jobApplication.getId())
                .applicationType(jobApplication.getApplicationType().getDescription())
                .place(jobApplication.getPlace())
                .result(jobApplication.getResult().getDescription())
                .submissionStatus(String.valueOf(jobApplication.isSubmissionStatus()))
                .resultStatus(String.valueOf(jobApplication.isResultStatus()))
                .applicationStartDate(jobApplication.getApplicationStartDate())
                .applicationCloseDate(jobApplication.getApplicationCloseDate())
                .applicationDetails(applicationDetailList)
                .build();
    }
}
