package com.ycyw.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ycyw.api.dto.ConversationResponseDTO;
import com.ycyw.api.service.ConversationService;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> getConversations(Authentication authentication) {
        return ResponseEntity.ok(conversationService.getConversations(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<ConversationResponseDTO> createConversation(Authentication authentication) {
        return ResponseEntity.ok(conversationService.createConversation(authentication.getName()));
    }
}