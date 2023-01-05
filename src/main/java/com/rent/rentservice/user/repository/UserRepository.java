package com.rent.rentservice.user.repository;

import com.rent.rentservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
