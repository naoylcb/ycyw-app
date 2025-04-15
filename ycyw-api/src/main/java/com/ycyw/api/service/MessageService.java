package com.ycyw.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycyw.api.dto.CreateMessageDTO;
import com.ycyw.api.dto.MessageResponseDTO;
import com.ycyw.api.model.Conversation;
import com.ycyw.api.model.Message;
import com.ycyw.api.model.AppUser;
import com.ycyw.api.repository.ConversationRepository;
import com.ycyw.api.repository.MessageRepository;
import com.ycyw.api.repository.UserRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public List<MessageResponseDTO> getMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public MessageResponseDTO createMessage(CreateMessageDTO createMessageDTO, String authorEmail) {
        Conversation conversation = conversationRepository.findById(createMessageDTO.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        AppUser author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setConversation(conversation);
        message.setAuthor(author);
        message.setContent(createMessageDTO.getContent());

        Message savedMessage = messageRepository.save(message);
        MessageResponseDTO messageResponseDTO = convertToDTO(savedMessage);

        simpMessagingTemplate.convertAndSend("/chat/messages", messageResponseDTO);

        return messageResponseDTO;
    }

    private MessageResponseDTO convertToDTO(Message message) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setId(message.getId());
        messageResponseDTO.setConversation(message.getConversation().getId());
        messageResponseDTO.setAuthor(message.getAuthor().getId());
        messageResponseDTO.setContent(message.getContent());
        messageResponseDTO.setCreatedAt(message.getCreatedAt());

        return messageResponseDTO;
    }
}