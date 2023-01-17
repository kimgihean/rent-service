package com.rent.rentservice.post.service;

import com.mysql.cj.Session;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.exception.SessionIdNotFoundException;
import com.rent.rentservice.post.exception.SessionNotFoundException;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.UserNotFoundException;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.util.session.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 글 등록
    public void create(PostCreateForm request,HttpSession session) throws Exception{

        //세션 아웃 검사
        if(!SessionUtil.isValidSession(session)) {
            throw new SessionNotFoundException();
        }

        //회원 정보 조회 검사
        User loginSessionID = userRepository.findById(SessionUtil.getLoginMemberIdn(session))
                .orElseThrow(UserNotFoundException::new);

        // request로 부터 post 저장

        Post post = Post.builder()
                .userID(loginSessionID)
                .title(request.getTitle())
                .text(request.getText())
                .favorite(0)
                .build();

        postRepository.save(post);
    }

    // 전체 게시글 조회
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    // 검색에 따른 게시글 조회
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
