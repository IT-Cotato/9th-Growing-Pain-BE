package cotato.growingpain.security.jwt.dto.response;

public record ReissueResponse(
        String accessToken,
        String refreshToken
) {
    public static ReissueResponse from(String accessToken, String refreshToken) {
        return new ReissueResponse(accessToken, refreshToken);
    }
}