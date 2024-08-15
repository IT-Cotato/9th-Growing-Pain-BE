package cotato.growingpain.log.dto;

import cotato.growingpain.log.domain.entity.ApplicationDetail;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record ApplicationDetailDTO(
        Long id,
        String title,
        String content
) {
    public static ApplicationDetailDTO fromEntity(ApplicationDetail applicationDetail) {
        return ApplicationDetailDTO.builder()
                .id(applicationDetail.getId())
                .title(applicationDetail.getTitle())
                .content(applicationDetail.getContent())
                .build();
    }
}
