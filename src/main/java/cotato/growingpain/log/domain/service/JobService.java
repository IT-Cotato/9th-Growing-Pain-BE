package cotato.growingpain.log.domain.service;

import cotato.growingpain.log.domain.dto.JobPostRequestDTO;
import cotato.growingpain.log.domain.dto.JobPostRetrieveDTO;
import cotato.growingpain.log.domain.entity.JobApplication;
import cotato.growingpain.log.domain.entity.JobPost;
import cotato.growingpain.log.domain.repository.ApplicationDetailRepository;
import cotato.growingpain.log.domain.repository.JobApplicationRepository;
import cotato.growingpain.log.domain.repository.JobPostRepository;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobPostRepository jobPostRepository;

    private final MemberRepository memberRepository;

    private final JobApplicationRepository jobApplicationRepository;

    private final ApplicationDetailRepository applicationDetailRepository;

    public JobPost createJobPost(JobPostRequestDTO jobPostRequest, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        log.info("Found member with ID: {}", member.getId());
        log.info(jobPostRequest.jobApplications().toString());

        JobPost jobPost = jobPostRequest.toEntity(member);

        log.info("Creating job post with job part: {}", jobPost.getJobPart());
        log.debug("JobPost details: {}", jobPost);

        JobPost savedJobPost = jobPostRepository.save(jobPost);

        jobPost.getJobApplications().forEach(jobApplication -> {
            jobApplication.setJobPost(savedJobPost);
            JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

            jobApplication.getApplicationDetails().forEach(applicationDetail -> {
                applicationDetail.setJobApplication(savedJobApplication);
                applicationDetailRepository.save(applicationDetail);
            });
        });

        return savedJobPost;
    }

    public List<JobPostRetrieveDTO> jobPostRetrieveList(final Long memberId) {
        log.debug("Fetching job posts for member ID: {}", memberId);

        List<JobPost> jobPosts = jobPostRepository.findByMemberId(memberId);

        return jobPosts.stream()
                .map(JobPostRetrieveDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
