package com.rent.rentservice.post.controller;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController // 이 annotation을 사용하면 메서드마다 ResponseBody를 따로 선언해줄 필요 없음.
public class PostController {

    private PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * POST 전체 조회하기 //get method
     * //todo return data type 학습
     */
    @GetMapping("/posts")
    public void list(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        //return
    }

    /**
     * POST CREATE //post method
     */
    @PostMapping("/posts/create")
    public String createPost() {

        return "redirect:/";
    }

    /**
     * POST CREATE 화면 //get method
     */


}
