package cotato.growingpain.member.domain;

import lombok.Getter;

@Getter
public enum MemberRole {

    GENERAL("ROLE_GENERAL"),
    MEMBER("ROLE_MEMBER");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }
}