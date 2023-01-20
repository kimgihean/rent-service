package com.rent.rentservice.user.repository;

import com.rent.rentservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @description User 도메인 레포지토리
 * @author 김승진
 * @since 2023.01.07
 */

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
