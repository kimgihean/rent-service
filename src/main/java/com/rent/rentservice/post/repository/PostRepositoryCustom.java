package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.request.SearchForm;

import java.util.List;

/**
 * @description Repository�� custom�ؼ� ����� �������̽�
 * @author �����
 * @since 23.01.20
 */
public interface PostRepositoryCustom {
    List<Post> findBySearchUsingQueryDsl(SearchForm condition);
}
