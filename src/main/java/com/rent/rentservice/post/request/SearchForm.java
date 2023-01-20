package com.rent.rentservice.post.request;

import com.rent.rentservice.util.search.SearchType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description �˻� �Է� ��
 * @author �����
 * @since 23.01.20
 */
@Data
public class SearchForm {
    @NotBlank(message = "�˻�� �Է��ϼ���")
    String content;
    SearchType type; // title, writer, title and context

    public SearchForm(String content, SearchType type) {
        this.content = content;
        this.type = type;
    }
}
