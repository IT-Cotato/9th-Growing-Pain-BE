package cotato.growingpain.log.domain;

import lombok.Getter;

@Getter
public enum Result{

    PENDING("대기중"),
    PASSED("합격"),
    FAILED("불합격");

    private final String description;

    Result(String description) {
        this.description = description;
    }
}