package com.rent.rentservice.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.post.domain.QPost;
import com.rent.rentservice.post.repository.PostRepository;
import com.rent.rentservice.post.request.PostCreateForm;
import com.rent.rentservice.post.request.PostUpdateForm;
import com.rent.rentservice.post.request.SearchForm;
import com.rent.rentservice.post.service.PostService;
import com.rent.rentservice.user.domain.User;
import com.rent.rentservice.user.repository.UserRepository;
import com.rent.rentservice.user.request.JoinForm;
import com.rent.rentservice.user.request.LoginForm;
import com.rent.rentservice.user.service.UserService;
import com.rent.rentservice.util.encryption.AES256;
import com.rent.rentservice.util.queryCustom.SearchType;
import com.rent.rentservice.util.session.SessionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description PostController 테스트코드
 * @author 김기현
 * @since 2023.01.16
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc // MockMvc 빈으로 등록
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AES256 aes256;

    @Autowired
    EntityManager em;

    SessionUtil sessionUtil;
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

        // 포스트 전체 삭제
        postRepository.deleteAll();
    }

    @Test @DisplayName("아이템 생성시 DB 저장")
    void test1() throws Exception {

        // given
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(0)
                .text("내용 테스트")
                .build();

        // objectMapper : Json 타입으로 convert
        String postJson = objectMapper.writeValueAsString(postRequest);

        // when
        mockMvc.perform(post("/Home/item-list/post")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)  // 객체를 변환해줄 타입
                        .content(postJson))             // 위 타입으로 저장할 객체
                .andExpect(status().isOk())             // status 응답
                .andDo(print());                        // 응답값 프린트

        // then
        Post post = postRepository.findAll().get(0);

        assertEquals(1L, post.getPostID());         // 두 개체의 값이 같은지 확인
        assertEquals("제목 테스트", post.getTitle());
        assertEquals(0, post.getFavorite());
        assertEquals("내용 테스트", post.getText());
    }

    @Test @DisplayName("검색 조회 요청")
    void test2() throws Exception {
        // given

        // 1. 아이템 게시글 저장
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목 테스트")
                .favorite(0)
                .text("내용 테스트")
                .build();

        postService.create(postRequest, session);

        SearchForm request = SearchForm.builder()
                .content("제목 검색")
                .type(SearchType.valueOf("title"))
                .build();

        String searchJson = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(get("/Home/item-list?")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchJson))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test @DisplayName("QueryDsl 동작 확인")
    void test3() {
        // given
        // session 으로 부터 사용자 받기
        User user = userRepository.findById(sessionUtil.getLoginMemberIdn(session))
                .get();

        // post 만들기
        Post request = Post.builder()
                .title("제목 테스트")
                .userID(user)
                .favorite(0)
                .text("내용 테스트")
                .build();

        // JPAQueryFactory 만들기
        em.persist(request);
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // when
        QPost qPost = new QPost("post");

        Post result = queryFactory
                .selectFrom(qPost)
                .fetchOne();

        // then
        assertThat(result).isEqualTo(request);
    }

    @Test @DisplayName("아이템 세부 사항")
    void test4() throws Exception {
        // given
        // 1. 아이템 게시글 저장
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목")
                .favorite(0)
                .text("내용")
                .build();

        postService.create(postRequest, session);

        Long postIdn = postRepository.findAll().get(0)
                .getPostID();

        // when
        String detailJson = objectMapper.writeValueAsString(postIdn);

        mockMvc.perform(get("/Home/item-list/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(detailJson))
                .andExpect(status().isOk())
                .andDo(print());
        // then

    }

    @Test @DisplayName("게시글 수정")
    void test5() throws Exception{
        //todo 400 error 수정
        //given
        //게시글 저장
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목")
                .favorite(0)
                .text("내용")
                .build();

        postService.create(postRequest, session);

        //게시글 수정
        PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                .title("제목 변경")
                .text("내용 변경")
                .build();

        String updatePostJson = objectMapper.writeValueAsString(postUpdateForm);
        String existPostId = objectMapper
                .writeValueAsString(postRepository.findAll().get(0).getPostID());

        //when
        mockMvc.perform(patch("/Home/item-list/update-{id}", existPostId)
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatePostJson)
                    .content(existPostId))
                        .andExpect(status().isOk())
                                .andDo(print()); // 400
        //then

    }

    @Test @DisplayName("게시글 삭제")
    void test6() throws Exception{
        //given
        //게시글 저장
        PostCreateForm postRequest = PostCreateForm.builder()
                .title("제목")
                .favorite(0)
                .text("내용")
                .build();

        postService.create(postRequest, session);

        String existPostId = objectMapper
                .writeValueAsString(postRepository.findAll().get(0).getPostID());

        mockMvc.perform(delete("/Home/item-list/delete-{id}", existPostId)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(existPostId))
                .andExpect(status().isOk())
                .andDo(print());
        //when

        //then

    }
}
