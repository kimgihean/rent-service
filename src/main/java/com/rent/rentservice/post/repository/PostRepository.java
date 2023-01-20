package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findByPostID(Long ID);
}
