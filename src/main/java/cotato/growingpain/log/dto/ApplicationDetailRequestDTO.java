package cotato.growingpain.log.dto;

import cotato.growingpain.log.entity.ApplicationDetail;
import cotato.growingpain.log.entity.JobApplication;

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
