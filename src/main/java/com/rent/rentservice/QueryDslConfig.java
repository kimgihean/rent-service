package com.rent.rentservice;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rent.rentservice.post.repository.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rent.rentservice.post.domain.Post;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @description QueryDsl Configuration Class
 * @description make JPAQueryFactory Bean
 * @author ±è±âÇö
 * @since 2023.01.20
 */
@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
