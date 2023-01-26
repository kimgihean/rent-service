package com.rent.rentservice.util.queryCustom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import static com.rent.rentservice.post.domain.QPost.post;

@Component
public class SearchUtil {

    // null ���� ������ Safe �� ó��
    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }

    // ������ isSearchable �޼ҵ�� �����丵
    public static BooleanBuilder isSearchable(String content, SearchType searchType) {
        if(searchType == SearchType.title) { // ����
            return titleEqual(content);
        }
        else if(searchType == SearchType.writer) { // �����
            return userEqual(content);
        }
        else { // ���� + ����
            return titleEqual(content).or(contentEqual(content));
        }
    }

    // �����, ����, ���뿡 �´� ������ ����
    static BooleanBuilder userEqual(String content) {return nullSafeBuilder(() -> post.userID.nickName.contains(content));}
    static BooleanBuilder titleEqual(String content) {
        return nullSafeBuilder(() -> post.title.contains(content));
    }
    static BooleanBuilder contentEqual(String content) {
        return nullSafeBuilder(() -> post.text.contains(content));
    }
}
