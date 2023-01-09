package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * 검색 기능
     */
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByPostID(Long ID);
}
