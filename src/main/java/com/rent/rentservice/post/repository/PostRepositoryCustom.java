package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.request.SearchForm;

import java.util.List;

/**
 * @description Repository를 custom해서 사용할 인터페이스
 * @author 김기현
 * @since 23.01.20
 */
public interface PostRepositoryCustom {
    List<Post> findBySearchUsingQueryDsl(SearchForm condition);
}
