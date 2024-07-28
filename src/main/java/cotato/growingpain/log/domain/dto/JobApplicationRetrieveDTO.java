package cotato.growingpain.log.domain.dto;

import cotato.growingpain.log.domain.entity.JobApplication;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record JobApplicationRetrieveDTO(
        String applicationType,
        String status
) {
    public static JobApplicationRetrieveDTO fromEntity(JobApplication jobApplication) {
        String status = jobApplication.getResult().toString();

        if ("DOCUMENT".equals(jobApplication.getApplicationType().toString())) {
            status = jobApplication.getResult() != null ? jobApplication.getResult().toString() : null;
        } else if ("INTERVIEW".equals(jobApplication.getApplicationType().toString())) {
            status = jobApplication.getResult() != null ? jobApplication.getResult().toString() : null;
        }

        return JobApplicationRetrieveDTO.builder()
                .applicationType(String.valueOf(jobApplication.getApplicationType()))
                .status(status)
                .build();
    }
}
