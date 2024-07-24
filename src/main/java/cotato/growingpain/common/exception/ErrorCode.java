package cotato.growingpain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //회원가입
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "존재하는 이메일입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "존재하는 닉네임입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 패스워드입니다."),

    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일이 존재하지 않습니다."),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인 요청에 실패했습니다."),
    JWT_NOT_EXISTS(HttpStatus.NO_CONTENT, "Jwt 토큰이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "해당 refresh 토큰이 DB에 존재하지 않습니다."),
    REISSUE_FAIL(HttpStatus.UNAUTHORIZED, "액세스 토큰 재발급 요청 실패"),

    //커뮤니티
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 게시글를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}