package cotato.growingpain.auth.dto.request;

public record LogoutRequest(
        String accessToken,
        String refreshToken
) {
}