package cotato.growingpain.log.dto.response;

import java.util.List;

public record JobApplicationListResponse(
        List<JobApplicationResponse> jobApplicaionList
) {
}
