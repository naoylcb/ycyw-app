package com.ycyw.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ycyw.api.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByCustomerIdOrSupportId(Long customerId, Long supportId);
} 