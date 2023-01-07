package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * 검색 기능
     */
    List<Post> findByTitleContaining(String keyword);
}
