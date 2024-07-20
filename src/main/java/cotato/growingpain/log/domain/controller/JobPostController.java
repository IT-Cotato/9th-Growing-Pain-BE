package cotato.growingpain.log.domain.controller;

import cotato.growingpain.log.domain.dto.JobPostRequestDTO;
import cotato.growingpain.log.domain.entity.JobPost;
import cotato.growingpain.log.domain.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
