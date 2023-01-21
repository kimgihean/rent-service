package com.rent.rentservice.post.service;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.exception.SessionNotFoundException;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.search.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.transaction.Transactional;

import java.util.List;

import static com.rent.rentservice.util.search.SearchType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PostServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    MockHttpSession session;

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
        PostCreateForm postRequest1 = PostCreateForm.builder()
                .title("한글로 검색합니다")
                .favorite(0)
                .text("가나다라마바사")
                .build();
        postService.create(postRequest1, session);

        PostCreateForm postRequest2 = PostCreateForm.builder()
                .title("영어로 검색합니다")
                .favorite(0)
                .text("ABCDEFG")
                .build();
        postService.create(postRequest2, session);

        // when
        List<Post> SearchedTitleList = postService
                .findBySearch(new SearchForm("한글", title));
        List<Post> SearchedWriterList = postService
                .findBySearch(new SearchForm("홍길동", writer));
        List<Post> SearchedTitleContextList = postService
                .findBySearch(new SearchForm("ABC", titleAndContext));

        // then todo : List 가 비어있는 문제 해결
        assertThat(SearchedTitleList).extracting("title")
                .containsExactly("한글로 검색합니다");
        assertThat(SearchedWriterList).extracting("title")
                .containsExactly("한글로 검색합니다", "영어로 검색합니다");
        assertThat(SearchedTitleContextList).extracting("title")
                .containsExactly("영어로 검색합니다");
    }

}
