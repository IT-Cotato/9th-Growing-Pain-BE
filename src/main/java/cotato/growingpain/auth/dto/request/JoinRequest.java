package cotato.growingpain.auth.dto.request;

import cotato.growingpain.member.domain.MemberJob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JoinRequest (

        @NotBlank(message = "로그인할 아이디를 입력해주세요")
        String email,

        @NotBlank(message = "사용할 비밀번호를 입력해주세요")
        @Size(min = 8, max = 16, message = "비밀번호는 8자 ~ 16자 사이여야 합니다.")
        String password,

        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        String name,

        @NotBlank(message = "분야는 필수 입력 항목입니다.")
        String field,

        @NotBlank(message = "소속은 필수 입력 항목입니다.")
        String belong,

        MemberJob job
) {
}