package cotato.growingpain.member.domain;

import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.security.oauth.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

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
}