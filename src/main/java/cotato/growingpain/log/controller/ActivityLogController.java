package cotato.growingpain.log.controller;

import cotato.growingpain.common.Response;
import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.dto.ActivityLogDTO;
import cotato.growingpain.log.service.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    @Operation(summary = "활동 기록 저장", description = "활동 기록을 등록하기 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<ActivityLog> createJobPost(@RequestBody @Valid ActivityLogDTO activityLogDto,
                                               @AuthenticationPrincipal Long memberId) {
        activityLogService.createActivityLog(activityLogDto, memberId);
        return Response.createSuccess("활동 기록 등록 완료", null);
    }
}
