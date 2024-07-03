package cotato.growingpain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //회원가입
    ID_DUPLICATED(HttpStatus.CONFLICT, "존재하는 id입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "존재하는 닉네임입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 패스워드입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}