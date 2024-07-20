package cotato.growingpain.log.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.log.domain.ApplicationType;
import cotato.growingpain.log.domain.Result;
import cotato.growingpain.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobApplication extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "job_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "applicaton_type")
    private ApplicationType applicationType;

    @Column(name = "place")
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Result result;

    @Column(name = "submission_status")
    private boolean submissionStatus;

    @Column(name = "application_start_date")
    private String applicationStartDate;

    @Column(name = "job_application_close_date")
    private String applicationCloseDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id")
    @JsonBackReference
    private JobPost jobPost;

    @OneToMany(mappedBy = "jobApplication")
    @JsonManagedReference
    private final List<ApplicationDetail> applicationDetails = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public JobApplication(
            ApplicationType applicationType,
            String place,
            Result result,
            boolean submissionStatus,
            String applicationStartDate,
            String applicationCloseDate,
            Member member,
            JobPost jobPost,
            List<ApplicationDetail> applicationDetails
    ) {
        // Relation Column
        this.member = member;
        this.jobPost = jobPost;

        // Information Column
        this.applicationType = applicationType;
        this.place = place;
        this.result = result;
        this.submissionStatus = submissionStatus;
        this.applicationStartDate = applicationStartDate;
        this.applicationCloseDate = applicationCloseDate;
    }

    public void addApplicationDetail(ApplicationDetail applicationDetail) {
        this.applicationDetails.add(applicationDetail);
        applicationDetail.setJobApplication(this);
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
        if (!jobPost.getJobApplications().contains(this)) {
            jobPost.getJobApplications().add(this);
        }
    }

}
