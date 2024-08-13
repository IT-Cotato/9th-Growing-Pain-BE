package cotato.growingpain.log.domain.service;

import cotato.growingpain.log.domain.dto.JobPostListRetrieveDTO;
import cotato.growingpain.log.domain.dto.JobPostRequestDTO;
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

    public void createJobPost(final JobPostRequestDTO jobPostRequest, Long memberId) {

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

    }

    public List<JobPostListRetrieveDTO> jobPostRetrieveList(final Long memberId) {
        List<JobPost> jobPosts = jobPostRepository.findByMemberId(memberId);

        return jobPosts.stream()
                .map(JobPostListRetrieveDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public void updateJobApplication(Long jobPostId, JobPostRequestDTO request, Long memberId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("JobPost not found with ID: " + jobPostId));

        if (!jobPost.getMember().getId().equals(memberId)) {
            throw new RuntimeException("Member not authoriuzed to update this JobPost");
        }

        jobPost.update(request, jobApplicationRepository, applicationDetailRepository);
        jobPostRepository.save(jobPost);

        log.info("Updated JobPost with ID: {}", jobPostId);

    }

    public void deleteJobPost(Long jobPostId, Long memberId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("JobPost not found with ID" + jobPostId));

        jobPostRepository.delete(jobPost);
    }
}
