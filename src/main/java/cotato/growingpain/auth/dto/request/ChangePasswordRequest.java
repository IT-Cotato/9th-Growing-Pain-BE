package cotato.growingpain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(

        @NotBlank(message = "기존 비밀번호는 필수 입력 항목입니다.")
        String currentPassword,

        @NotBlank(message = "새 비밀번호는 필수 입력 항목입니다.")
        String newPassword
) {
}