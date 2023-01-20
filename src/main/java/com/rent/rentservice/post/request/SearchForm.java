package com.rent.rentservice.post.request;

import com.rent.rentservice.util.search.SearchType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 검색 입력 폼
 * @author 김기현
 * @since 23.01.20
 */
@Data
public class SearchForm {
    @NotBlank(message = "검색어를 입력하세요")
    String content;
    SearchType type; // title, writer, title and context

    public SearchForm(String content, SearchType type) {
        this.content = content;
        this.type = type;
    }
}
