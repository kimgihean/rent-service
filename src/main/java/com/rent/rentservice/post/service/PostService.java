package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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
     * 검색(title)에 따른 게시글 조회
     */
    @Transactional
    public List<Post> findBySearch(String keyword) {
        List<Post> postList = postRepository.findByTitleContaining(keyword);
        List<Post> postListBySearch = new ArrayList<>();

        if(postList.isEmpty()) return postListBySearch; //todo exception 처리

        for(Post post : postList) {
            postListBySearch.add(this.convertEntityInPost(post));
        }

        return postListBySearch;
    }

    /**
     * @description build the post constructor
     * @param post
     * @return post builder
     */
    private Post convertEntityInPost(Post post) {
        return post.builder()
                .postID(post.getPostID())
                .userID(post.getUserID())
                .title(post.getTitle())
                .period(post.getPeriod())
                .favorite(post.getFavorite())
                .build();


    }

    /**
     * @description Find all post by post ID
     * @param ID
     * @return new post object
     */
    public List<Post> findByID(Long ID) {
        List<Post> postList = postRepository.findByPostID(ID);
        List<Post> postListByID = new ArrayList<>();

        if(postList.isEmpty()) return postListByID; //todo exception 처리

        for(Post post : postList) {
            postListByID.add(this.convertEntityInPost(post));
        }

        return postListByID;
    }
    /**
     * address에 따른 게시글 조회
     */
}
