package cotato.growingpain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRegisterRequest(

        @NotBlank(message = "내용은 필수 항목입니다.")
        String content
){
}