package cotato.growingpain.post.dto.response;

import cotato.growingpain.post.domain.entity.Post;
import java.time.LocalDateTime;

public record PostResponse(

        Long postId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String title,
        String content,
        //String postImageUrl,
        String parentCategory,
        String subCategory,
        int likeCount,
        int commentCount,
        Boolean isDeleted,
        String memberNickname,
        String profileImageUrl,
        String memberField
) {
    public static PostResponse from(Post post) {
        return new PostResponse(

                post.getId(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getTitle(),
                post.getContent(),
                //post.getImageUrl(),
                post.getParentCategory() != null ? post.getParentCategory().name() : null,
                post.getSubCategory() != null ? post.getSubCategory().name() : null,
                post.getLikeCount(),
                post.getComments().size(),
                post.isDeleted(),
                post.getMember().getName(),
                post.getMember().getProfileImageUrl(),
                post.getMember().getField()
        );
    }
}
