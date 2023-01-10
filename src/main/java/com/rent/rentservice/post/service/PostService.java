package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**
     * 글 등록
     */
    public void create(PostCreateForm request) throws Exception{
        //todo XSS in post create
        //todo address 에 맞게 게시글 작성
        //좋아요 초기 갯수 0 으로 설정
        int initFavorite = 0;

        Post post = Post.builder()
                .title(request.getTitle())
                .text(request.getText())
                .favorite(initFavorite)
                .build();

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
    private Post convertEntityInPost(@NotNull Post post) {
        return post.builder()
                .title(post.getTitle())
                .favorite(post.getFavorite())
                .text(post.getText())
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
