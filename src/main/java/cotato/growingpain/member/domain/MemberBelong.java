package cotato.growingpain.member.domain;

import lombok.Getter;

@Getter
public enum MemberBelong {
    
    ENTER_SCHOOL_NAME("학교명 입력"),
    ENTER_COMPANY_NAME("회사명 입력"),
    NO_AFFILIATION("소속 없음");

    private final String description;

    MemberBelong(String description) {
        this.description = description;
    }
}