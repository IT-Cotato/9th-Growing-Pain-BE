package cotato.growingpain.post.dto.request;

import cotato.growingpain.post.PostCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRegisterRequest(

        @NotBlank(message = "제목은 필수 항목입니다.")
        @Size(max = 50)
        String title,

        @NotBlank(message = "내용은 필수 항목입니다.")
        @Size(max = 3000)
        String content,

        String imageUrl,

        PostCategory category
){
}