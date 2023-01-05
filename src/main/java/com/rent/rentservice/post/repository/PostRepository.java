package com.rent.rentservice.post.repository;

import com.rent.rentservice.post.domain.Post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    //Optional<Post> findBy();
}
