package cotato.growingpain.comment.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cotato.growingpain.common.domain.BaseTimeEntity;
import cotato.growingpain.member.domain.entity.Member;
import cotato.growingpain.post.domain.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private Comment comment;

    public CommentLike(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }

    public static CommentLike of(Member member, Comment comment) {
        return new CommentLike(member, comment);
    }

    public void increaseCommentLikeCount() {
        comment.increaseLikeCount();
    }
}