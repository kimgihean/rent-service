package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.request.SearchForm;

import java.util.List;
import java.util.Optional;

/**
 * @description Repository�� custom�ؼ� ����� �������̽�
 * @author �����
 * @since 23.01.20
 */
public interface PostRepositoryCustom {
    List<Post> findBySearchUsingQueryDsl(SearchForm condition);

    // ������ ��ȸ�� ���� �޼ҵ�
    Post updateViewCount(Long requestId);
}
