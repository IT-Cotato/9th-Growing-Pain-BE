package cotato.growingpain.log.dto.request;

import cotato.growingpain.log.domain.entity.ApplicationDetail;
import cotato.growingpain.log.domain.entity.JobApplication;

public record ApplicationDetailRequestDTO(
        Long id,
        String title,
        String content) {

    public ApplicationDetail toEntity(JobApplication jobApplication) {
        return ApplicationDetail.builder()
                .title(this.title)
                .content(this.content)
                .jobApplication(jobApplication)
                .build();
    }

}
