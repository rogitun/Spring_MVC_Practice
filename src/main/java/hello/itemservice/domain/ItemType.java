package hello.itemservice.domain;

import lombok.Getter;

@Getter
public enum ItemType {
    BOOK("도서"),FOOD("음식"),ETC("기타");

    private final String desc;

    ItemType(String desc) {
        this.desc = desc;
    }
}
