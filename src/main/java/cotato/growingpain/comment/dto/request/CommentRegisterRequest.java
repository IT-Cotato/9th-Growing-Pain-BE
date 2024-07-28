package cotato.growingpain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRegisterRequest(

        @NotBlank(message = "내용은 필수 항목입니다.")
        @Size(max = 500)
        String content
){
}