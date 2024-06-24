package cotato.growingpain.post;

import lombok.Getter;

@Getter
public enum PostCategory {

    ALL("전체"),
    FREE("자유게시판"),
    TEAM("팀원 모집"),
    PORTFOLIO("포트폴리오"),
    CONTEST("공모전"),
    PROJECT("프로젝트"),
    STUDY("스터디");

    private final String description;

    PostCategory(String description) {
        this.description = description;
    }
}