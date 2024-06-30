package cotato.growingpain.log.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.log.domain.ActivityType;
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
public class ActivityLog extends BaseTimeEntity {
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
    @Column(name = "activity_type")
    private ActivityType activityType;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "performance")
    private String performance;

    @Column(name = "role")
    private String role;

    @Column(name = "contribution")
    private int contribution;

    @Column(name = "activity_start_date")
    private String activityStartDate;

    @Column(name = "activity_close_date")
    private String activityCloseDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "file_url")
    private String fileUrl;

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

    @Builder
    public ActivityLog(
            ActivityType activityType,
            String activityName,
            String content,
            String performance,
            String role,
            Integer contribution,
            String activityStartDate,
            String activityCloseDate,
            String imageUrl,
            String fileUrl,
            Member member
    ) {
        // Relation Column
        this.member = member;

        // Information Column
        this.activityType = activityType;
        this.activityName = activityName;
        this.content = content;
        this.performance = performance;
        this.role = role;
        this.contribution = contribution;
        this.activityStartDate = activityStartDate;
        this.activityCloseDate = activityCloseDate;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }
}
