package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.exception.SessionNotFoundException;
import com.rent.rentservice.post.exception.UpdatePostSessionException;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.PostUpdateForm;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.exception.UserNotFoundException;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.util.session.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.rent.rentservice.util.session.SessionUtil.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 글 등록
    public void create(PostCreateForm request,HttpSession session) throws Exception{
        //세션 아웃 검사
        if(SessionUtil.checkValidSession(session)) {
            throw new SessionNotFoundException();
        }

        //회원 정보 조회 검사
        User loginSessionID = userRepository.findById(getLoginMemberIdn(session))
                .orElseThrow(UserNotFoundException::new);

        Post post = Post.builder()
                .userID(loginSessionID)
                .title(request.getTitle())
                .text(request.getText())
                .favorite(0)
                .viewCount(0)
                .build();

        postRepository.save(post);
    }

    // 검색에 따른 게시글 조회
    @Transactional
    public List<Post> findBySearch(SearchForm condition) {
        List<Post> searchPostList = postRepository.findBySearchUsingQueryDsl(condition);
        return searchPostList;
    }

    // 게시글 상세 조회
    public Post postDetail(Long requestID){
        Post byId = postRepository.findById(requestID)
                .orElse(null);

        //Post post = postRepository.updateViewCount(requestID);

        byId.setViewCount(byId.getViewCount() + 1);

        return byId;
    }

    // 게시글 업데이트
    public Post update(Long id, PostUpdateForm postUpdateForm, HttpSession session) {
        Post existPost = postRepository.findById(id)
                .orElse(null);

        // 세션에 등록된 사용자의 게시글에 수정 삭제 권한 부여 -> 예외처리
        checkPostAuth(session, existPost);

        if(existPost.getTitle() != postUpdateForm.getTitle())
            existPost.setTitle(postUpdateForm.getTitle());
        if(existPost.getText() != postUpdateForm.getText())
            existPost.setText(postUpdateForm.getText());

        postRepository.save(existPost);

        return existPost;

    }

    // 게시글 삭제
    public void delete(Long id, HttpSession session) {
        Post existPost = postRepository.findById(id)
                .orElse(null);

        // 세션에 등록된 사용자의 게시글에 수정 삭제 권한 부여 -> 예외처리
        checkPostAuth(session, existPost);

        postRepository.delete(existPost);
    }
}
