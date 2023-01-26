package com.rent.rentservice.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.request.SearchForm;
import org.springframework.stereotype.Repository;
import com.rent.rentservice.util.queryCustom.SearchUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    // 검색 custom method
    @Override
    public List<Post> findBySearchUsingQueryDsl(SearchForm condition) {
        // 생성한 where 조건절 + 시간에 따른 내림차순
        return jpaQueryFactory
                .selectFrom(post)
                .where(SearchUtil.isSearchable(condition.getContent(), condition.getType()))
                .fetch();
    }

    // 아이템 조회수 증가 메소드
    @Override
    public Post updateViewCount(Long requestId) {
        jpaQueryFactory
                .update(post)
                .set(post.viewCount, post.viewCount.add(1))
                .where(post.postID.eq(requestId))
                .execute();

        return jpaQueryFactory
                .selectFrom(post)
                .where(post.postID.eq(requestId))
                .fetchOne();
    }
}