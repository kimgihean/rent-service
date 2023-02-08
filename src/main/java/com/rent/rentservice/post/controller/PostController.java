package com.rent.rentservice.post.controller;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.PostUpdateForm;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.rent.rentservice.util.queryCustom.PageRequest;

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

    // 아이템 상세 조회 + 조회수 증가
    @GetMapping(value = "/Home/item-list/{id}")
    public Post item_detail(@RequestParam @PathVariable("id") Long request) throws Exception {
        Post post = postService.postDetail(request);
        return post;
    }

    // 아이템 업데이트
    @PatchMapping(value = "/Home/item-list/update-{id}")
    public Post itemUpdate(@RequestBody PostUpdateForm postUpdateForm, @PathVariable("id") Long id,HttpSession session) throws Exception{
        Post updatePost = postService.update(id, postUpdateForm, session);

        return updatePost;
    }

    // 아이템 삭제
    @DeleteMapping(value = "/Home/item-list/delete-{id}")
    public void itemDelete(@PathVariable("id") Long id, HttpSession session) {
        postService.delete(id, session);
    }
}
