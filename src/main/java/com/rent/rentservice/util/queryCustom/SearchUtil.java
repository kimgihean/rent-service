package com.rent.rentservice.util.queryCustom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import static com.rent.rentservice.post.domain.QPost.post;

@Component
public class SearchUtil {

    // null 값이 오더라도 Safe 로 처리
    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }

    // 조건절 isSearchable 메소드로 리팩토링
    public static BooleanBuilder isSearchable(String content, SearchType searchType) {
        if(searchType == SearchType.title) { // 제목
            return titleEqual(content);
        }
        else if(searchType == SearchType.writer) { // 사용자
            return userEqual(content);
        }
        else { // 제목 + 내용
            return titleEqual(content).or(contentEqual(content));
        }
    }

    // 사용자, 제목, 내용에 맞는 조건절 생성
    static BooleanBuilder userEqual(String content) {return nullSafeBuilder(() -> post.userID.nickName.contains(content));}
    static BooleanBuilder titleEqual(String content) {
        return nullSafeBuilder(() -> post.title.contains(content));
    }
    static BooleanBuilder contentEqual(String content) {
        return nullSafeBuilder(() -> post.text.contains(content));
    }
}
