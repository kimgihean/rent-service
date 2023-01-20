package com.rent.rentservice.mail.repository;

import com.rent.rentservice.mail.domain.AuthNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description 이메일 인증 리포지토리
 * @author 김승진
 * @since 2023.01.20
 */

@Repository
public interface MailRepository extends JpaRepository<AuthNum, Long> {

}
