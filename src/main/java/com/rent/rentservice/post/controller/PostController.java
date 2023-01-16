package com.rent.rentservice.post.controller;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.service.PostService;
import com.rent.rentservice.util.session.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    /**
     * POST 전체 조회하기 //get method
     * //todo return data type 학습
     */
    @GetMapping("/posts")
    public List<Post> list() {
        List<Post> posts = postRepository.findAll();

        return posts;
    }

    // 검색에 따른 전체 조회
    @GetMapping("/posts/search")
    public List<Post> searchList(String keyword) {
        List<Post> searchPosts = postRepository.findByTitleContaining(keyword);
        return searchPosts;
    }

    // 게시글 CREATE
    @PostMapping("/posts/create")
    public void create(@RequestBody @Valid PostCreateForm request,HttpSession session ,SessionUtil sessionUtil) throws Exception{
        postService.create(request, session, sessionUtil);
    }


}
