package cotato.growingpain.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.domain.entity.MemberJobApplication;
import cotato.growingpain.security.oauth.AuthProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Email
    @Column(name = "member_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_field")
    private String field;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_belong")
    private MemberBelong belong;

    @Column(name = "member_belong_detail")
    private String belongDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_job")
    private MemberJob job;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "auth_provider")
    private AuthProvider authProvider;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<MemberJobApplication> memberJobApplications = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<ActivityLog> activityLogs = new ArrayList<>();
}