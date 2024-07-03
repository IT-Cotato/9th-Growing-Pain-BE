package cotato.growingpain.auth.service;

import cotato.growingpain.common.exception.AppException;
import cotato.growingpain.common.exception.ErrorCode;
import cotato.growingpain.member.repository.MemberRepository;
import jakarta.validation.constraints.NotBlank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateService {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
    private final MemberRepository memberRepository;

    public void checkPasswordPattern(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void checkDuplicateNickName(String name) {
        if (memberRepository.findByName(name).isPresent()) {
            log.error("[회원 가입 실패]: 존재하는 닉네임 " + name);
            throw new AppException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }

    public void checkDuplicateId(@NotBlank(message = "로그인할 아이디를 입력해주세요") String loginId) {
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            log.error("[회원 가입 실패]: 존재하는 id " + loginId);
            throw new AppException(ErrorCode.ID_DUPLICATED);
        }
    }
}