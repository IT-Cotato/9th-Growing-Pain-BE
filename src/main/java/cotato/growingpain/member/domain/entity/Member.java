package cotato.growingpain.member.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cotato.growingpain.comment.domain.entity.Comment;
import cotato.growingpain.comment.domain.entity.CommentLike;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.log.domain.entity.ActivityLog;
import cotato.growingpain.log.domain.entity.JobApplication;
import cotato.growingpain.log.domain.entity.JobPost;
import cotato.growingpain.member.domain.MemberJob;
import cotato.growingpain.member.domain.MemberRole;
import cotato.growingpain.post.domain.entity.Post;
import cotato.growingpain.post.domain.entity.PostLike;
import cotato.growingpain.post.domain.entity.PostSave;
import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import cotato.growingpain.replycomment.domain.entity.ReplyCommentLike;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    @Column(name = "member_name")   //닉네임
    private String name;

    @Column(name = "member_password")
    private String password;

    @Email
    @Column(name = "member_email")
    private String email;

    @Column(name = "member_field")  //분야
    private String field;

    @Column(name = "member_belong") //소속
    private String belong;

    @Enumerated(EnumType.STRING)    //소속 중 직업
    @Column(name = "member_job")
    private MemberJob job;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'GENERAL'")
    private MemberRole memberRole;

    @Column(name = "oauth_id")
    private String oauthId;

//    @Column(name = "auth_provider")
//    private AuthProvider authProvider;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<JobApplication> jobApplications = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<ActivityLog> activityLogs = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<PostSave> postSaves = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<ReplyComment> replyComments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<ReplyCommentLike> replyCommentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<JobPost> jobPosts = new ArrayList<>();

    @Builder
    public Member(String email, String password, String name, String field, String belong) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.field = field;
        this.belong = belong;
    }
}