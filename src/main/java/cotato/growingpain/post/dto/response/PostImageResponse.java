package cotato.growingpain.post.dto.response;

import cotato.growingpain.post.domain.entity.PostImage;

public record PostImageResponse(

        Long postImageId,
        String imageUrl
) {
    public static PostImageResponse from(PostImage postImage) {
        return new PostImageResponse(
                postImage.getId(),
                postImage.getImageUrl()
        );
    }
}