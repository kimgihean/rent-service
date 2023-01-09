package com.rent.rentservice.post.request;

import lombok.Data;

import java.util.Date;

@Data
public class PostCreateForm {
    private String title;
    //private Date period;
    //todo JPA Auditing을 사용하여 Entity에 생성수정시간 등록 자동화 추상 도메인 클래스 만들기
}
