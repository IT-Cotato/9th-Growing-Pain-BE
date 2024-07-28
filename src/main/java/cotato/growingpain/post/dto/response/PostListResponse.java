package cotato.growingpain.post.dto.response;

import cotato.growingpain.post.domain.entity.Post;
import java.util.List;

public record PostListResponse(List<Post> posts) {

}

