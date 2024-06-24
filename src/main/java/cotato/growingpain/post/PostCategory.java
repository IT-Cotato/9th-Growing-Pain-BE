package cotato.growingpain.post;

import lombok.Getter;

@Getter
public enum PostCategory {

    CATEGORY("카테고리"),
    COMPETITION("공모전"),
    PROJECT("프로젝트"),
    STUDY("스터디");

    private final String key;

    PostCategory(String key) {
        this.key = key;
    }
}