package cotato.growingpain.log.domain.controller;

import cotato.growingpain.log.domain.dto.JobPostRequestDTO;
import cotato.growingpain.log.domain.dto.JobPostRetrieveDTO;
import cotato.growingpain.log.domain.entity.JobPost;
import cotato.growingpain.log.domain.service.JobService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/job-posts")
@RequiredArgsConstructor
public class JobPostController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobPost> createJobPost(@RequestBody @Valid JobPostRequestDTO jobPostRequestDTO) {
        JobPost jobPost = jobService.createJobPost(jobPostRequestDTO);
        return ResponseEntity.ok(jobPost);
    }

    @GetMapping
    public ResponseEntity<List<JobPostRetrieveDTO>> getJobPosts(
            @RequestParam Long memberId) {
        List<JobPostRetrieveDTO> retrievedJobPost = jobService.jobPostRetrieveList(memberId);
        return ResponseEntity.ok(retrievedJobPost);
    }
}
