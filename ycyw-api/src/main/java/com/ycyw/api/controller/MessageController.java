package com.ycyw.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;

import com.ycyw.api.dto.CreateMessageDTO;
import com.ycyw.api.dto.MessageResponseDTO;
import com.ycyw.api.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getMessages(@RequestParam Long conversationId) {
        return ResponseEntity.ok(messageService.getMessages(conversationId));
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(@Valid @RequestBody CreateMessageDTO createMessageDTO, Authentication authentication) {
        return ResponseEntity.ok(messageService.createMessage(createMessageDTO, authentication.getName()));
    }
}