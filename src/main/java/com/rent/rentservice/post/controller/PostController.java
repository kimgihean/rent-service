package com.rent.rentservice.post.controller;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // 전체 조회
    @GetMapping(value = "/Home/item-list")
    public List<Post> list() {
        List<Post> posts = postRepository.findAll();

        return posts;
    }

    // 검색에 따른 전체 조회
    @GetMapping(value = "/Home/item-list?")
    public List<Post> searchList(@RequestParam SearchForm request) {
        List<Post> searchPosts = postService.findBySearch(request);
        return searchPosts;
    }

    // 게시글 CREATE
    @PostMapping(value = "/Home/item-list/post")
    public void create(@RequestBody @Valid PostCreateForm request,HttpSession session) throws Exception{
        postService.create(request, session);
    }

    // 아이템 상세 목록 조회
    @GetMapping(value = "/Home/item-list/item-id")
    public void item_detail() {

    }
}
