package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 글 등록
     */
    public void create(Post post) {
        // 게시글 등록의 유효성 검사 필요하지 않을 것 같음
        // 보안관련 사이트 간 스크립팅 XSS 확인하기
        //todo XSS in post create
        postRepository.save(post);
    }

    /**
     * 전체 게시글 조회
     */
    public List<Post> findAllPost() {

        return postRepository.findAll();
    }

    /**
     * id로 게시글 조회
     */

    /**
     * address에 따른 게시글 조회
     */

    /**
     * 검색에 따른 게시글 조회
     */
}
