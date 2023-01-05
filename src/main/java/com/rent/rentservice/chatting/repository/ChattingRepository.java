package com.rent.rentservice.chatting.repository;

import com.rent.rentservice.chatting.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
}
