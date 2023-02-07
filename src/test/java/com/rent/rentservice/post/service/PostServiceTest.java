package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.exception.SessionNotFoundException;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.repository.PostRepositoryImpl;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.session.SessionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.rent.rentservice.util.queryCustom.SearchType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PostServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostRepositoryImpl postRepositoryImpl;
    @Autowired
    EntityManager em;

    MockHttpSession session;
    SessionUtil sessionUtil;

    @BeforeEach
    void clean() throws Exception {
        // 임시 회원 정보 저장하고 로그인 시키기
        session = new MockHttpSession();
        JoinForm joinRequest = JoinForm.builder()
                .name("홍길동")
                .nickName("닉네임")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .password("1234")
                .address("경기도")
                .build();
        userService.join(joinRequest);

        LoginForm loginRequest = LoginForm.builder()
                .email("test@test.com")
                .password("1234")
                .build();
        userService.login(loginRequest, session);

        postRepository.deleteAll();
    }

    @Test @DisplayName("아이템 작성")
    void test1() throws Exception {
        //given
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(0)
                .text("내용 테스트")
                .build();

        //when
        postService.create(postRequest, session);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목 테스트", post.getTitle());
        assertEquals(0, post.getFavorite());
        assertEquals("내용 테스트", post.getText());
    }

    @Test @DisplayName("아이템 생성시 세션 아웃 검사")
    void test2() throws Exception {
        // given
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(0)
                .text("내용 테스트")
                .build();

        // when
        session.invalidate();

        // todo 오류 then :: invalidate 했으나 세션 아웃 검사해서 예외 처리 못함.
        assertThrows(SessionNotFoundException.class, () -> {
            postService.create(postRequest, session);
        });
    }

    @Test @DisplayName("검색으로 아이템 조회")
    void test3() throws Exception {
        // given
        // session 으로 부터 사용자 받기
        User user = userRepository.findById(sessionUtil.getLoginMemberIdn(session))
                .get();

        // post 만들기
        Post request1 = Post.builder()
                .title("제목 테스트 1")
                .userID(user)
                .favorite(0)
                .text("테스트 1")
                .build();

        Post request2 = Post.builder()
                .title("제목 테스트 2")
                .userID(user)
                .favorite(0)
                .text("내용 테스트 2")
                .build();

        // post Repository 에 저장
        postRepository.save(request1);
        postRepository.save(request2);


        // when
        List<Post> SearchedTitleList = postService
                .findBySearch(new SearchForm("제목", title));
        List<Post> SearchedWriterList = postService
                .findBySearch(new SearchForm("닉네임", writer));
        List<Post> SearchedTitleContextList = postService
                .findBySearch(new SearchForm("내용", titleAndContext));

        // then
        assertThat(SearchedTitleContextList.size()).isEqualTo(1);
        assertThat(SearchedTitleList.size()).isEqualTo(2);
        assertThat(SearchedWriterList).extracting("title")
                .containsExactlyInAnyOrder("제목 테스트 1", "제목 테스트 2");
    }

    @Test @DisplayName("아이템 상세 조회")
    void test4() throws Exception {
        // given
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목")
                .favorite(0)
                .text("내용")
                .build();

        postService.create(postRequest, session);

        Post post = postRepository.findAll().get(0);
        Long postId = post.getPostID();

        // when
        Post postDetail = postService.postDetail(postId);

        // then
        assertThat(post.getPostID()).isEqualTo(postDetail.getPostID());
        assertThat(post.getTitle()).isEqualTo(postDetail.getTitle());
    }

    @Test @DisplayName("조회수 증가 확인")
    void test5() throws Exception {
        //given
        // session 으로 부터 사용자 받기
        User user = userRepository.findById(sessionUtil.getLoginMemberIdn(session))
                .orElse(null);

        // post 만들기
        Post request1 = Post.builder()
                .title("제목")
                .userID(user)
                .favorite(0)
                .text("내용")
                .viewCount(10)
                .build();

        // post Repository 에 저장
        postRepository.save(request1);

        Post post = postRepository.findAll().get(0);
        Long postId = post.getPostID();

        //when
        Post postDetail = postService.postDetail(postId);

        //then
        //todo 조회수 증가 확인
        assertThat(post.getViewCount()).isEqualTo(postDetail.getViewCount());

    }
}
