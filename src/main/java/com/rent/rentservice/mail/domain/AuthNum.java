package com.rent.rentservice.mail.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @description 인증번호 저장 테이블 Entity
 * @author 김승진
 * @since 2023.01.20
 */

@Entity
@Data
@Table(name = "AuthNum")
@NoArgsConstructor
public class AuthNum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authNumId;

    private String email;

    private String authNum;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public Date regDate;

    @Builder
    public AuthNum(String email, String authNum) {
        this.email = email;
        this.authNum = authNum;
    }
}
