package cotato.growingpain.log.domain.entity;

import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
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
    private ApplicationType applicationTypep;

    @Column(name = "place")
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Result result;

    @Lob
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
    private LocalDateTime applicationStartDate;

    @Column(name = "job_application_close_date")
    private LocalDateTime applicationCloseDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

}
