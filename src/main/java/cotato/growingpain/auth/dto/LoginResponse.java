package cotato.growingpain.auth.dto;

import cotato.growingpain.security.jwt.Token;

public record LoginResponse(
        Token token,
        Long memberId,
        String name,
        String field,
        String profileImageUrl
) {
}
