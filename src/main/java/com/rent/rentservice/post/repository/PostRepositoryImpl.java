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
import com.rent.rentservice.util.search.SearchUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static com.rent.rentservice.post.domain.QPost.post;

/**
 * @description QueryDsl�� �̿��� ������ �ۼ��� Ŭ����
 * @description Implements PostRepositoryCustom
 * @author �����
 * @since 23.01.20
 */

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    // todo : �˻� �� �α��� �� ������� �ּҿ� �´� ������ ��ȸ�ǵ��� ����
    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    // �˻� custom method
    @Override
    public List<Post> findBySearchUsingQueryDsl(SearchForm condition) {
        // ������ where ������ + �ð��� ���� ��������
        return jpaQueryFactory
                .selectFrom(post)
                .where(SearchUtil.isSearchable(condition.getContent(), condition.getType()))
                .fetch();
    }
}