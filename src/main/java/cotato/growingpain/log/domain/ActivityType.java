package cotato.growingpain.log.domain;

import lombok.Getter;

@Getter
public enum ActivityType {

    EXTRA_ACTIVITY("대외활동"),
    VOLUNTEER_ACTIVITY("봉사활동"),
    PROJECT("프로젝트"),
    COMPETITIONS("공모전"),
    CLUB("동아리"),
    EXTRA_SPACE("여분통");

    private final String description;

    ActivityType(String description) {
        this.description = description;
    }
}