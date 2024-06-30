package cotato.growingpain.log.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.log.domain.ApplicationType;
import cotato.growingpain.log.domain.Result;
import cotato.growingpain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJobApplication extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "submission_status")
    private boolean submissionStatus;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_part")
    private String jobPart;

    @Column(name = "job_post_link")
    private String jobPostLink;

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

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public MemberJobApplication(
            ApplicationType applicationType,
            String place,
            Result result,
            String content,
            boolean submissionStatus,
            String companyName,
            String jobPart,
            String jobPostLink,
            String applicationStartDate,
            String applicationCloseDate,
            Member member
    ) {
        // Relation Column
        this.member = member;

        // Information Column
        this.applicationType = applicationType;
        this.place = place;
        this.result = result;
        this.content = content;
        this.submissionStatus = submissionStatus;
        this.companyName = companyName;
        this.jobPart = jobPart;
        this.jobPostLink = jobPostLink;
        this.applicationStartDate = applicationStartDate;
        this.applicationCloseDate = applicationCloseDate;
    }

}
