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

    // ������ ��ȸ�� ���� �޼ҵ�
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