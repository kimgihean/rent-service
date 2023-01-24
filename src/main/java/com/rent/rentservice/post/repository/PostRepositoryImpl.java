package com.rent.rentservice.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.domain.QPost;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.util.search.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static com.rent.rentservice.post.domain.QPost.post;

/**
 * @description QueryDsl을 이용해 쿼리를 작성할 클래스
 * @description Implements PostRepositoryCustom
 * @author 김기현
 * @since 23.01.20
 */

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    // todo : 검색 시 로그인 한 사용자의 주소에 맞는 아이템 조회되도록 수정
    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findBySearchUsingQueryDsl(SearchForm condition) {

        // 생성한 where 조건절 + 시간에 따른 내림차순
        return jpaQueryFactory
                .selectFrom(post)
                .where(isSearchable(condition.getContent(), condition.getType()))
                .fetch();
    }

    // null 값이 오더라도 Safe 로 처리
    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }

    // 사용자, 제목, 내용에 맞는 조건절 생성
    BooleanBuilder userEqual(String content) {
        return nullSafeBuilder(() -> post.userID.nickName.contains(content));
    }
    BooleanBuilder titleEqual(String content) {
        return nullSafeBuilder(() -> post.title.contains(content));
    }
    BooleanBuilder contentEqual(String content) {
        return nullSafeBuilder(() -> post.text.contains(content));
    }

    // 조건절 isSearchable 메소드로 리팩토링
    BooleanBuilder isSearchable(String content, SearchType searchType) {
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

}