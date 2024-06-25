package cotato.growingpain.log.domain;

import lombok.Getter;

@Getter
public enum ApplicationType {

    DOCUMENT("서류"),
    INTERVIEW("면접");

    private final String description;

    ApplicationType(String description) {
        this.description = description;
    }
}