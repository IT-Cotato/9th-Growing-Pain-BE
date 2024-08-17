package cotato.growingpain.post.dto.response;

import cotato.growingpain.post.domain.entity.Post;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String postImageUrl,
        String parentCategory,
        String subCategory,
        Integer likeCount,
        Boolean isDeleted,
        String memberNickname,
        String ProfileImage,
        String memberField
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getParentCategory() != null ? post.getParentCategory().name() : null,
                post.getSubCategory() != null ? post.getSubCategory().name() : null,
                post.getLikeCount(),
                post.isDeleted(),
                post.getMember().getName(),
                post.getMember().getProfileImageUrl(),
                post.getMember().getField()
        );
    }
}
