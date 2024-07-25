package cotato.growingpain.replycomment.repository;

import cotato.growingpain.replycomment.domain.entity.ReplyComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

}
