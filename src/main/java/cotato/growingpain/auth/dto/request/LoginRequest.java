package cotato.growingpain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "로그인할 아이디를 입력해주세요")
        String email,

        @NotBlank(message = "사용할 비밀번호를 입력해주세요")
        @Size(min = 8, max = 16, message = "비밀번호는 8자 ~ 16자 사이여야 합니다.")
        String password
) {
}