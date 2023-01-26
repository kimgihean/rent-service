package com.rent.rentservice.util.queryCustom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum SearchType {
    title("title"),
    writer("writer"),
    titleAndContext("context");

    @Getter
    private final String value;

    SearchType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static SearchType from(String value) {
        for(SearchType type : SearchType.values()) {
            if(type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    private String getValue() {
        return value;
    }
}
