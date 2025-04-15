package com.ycyw.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.ycyw.api.dto.ConversationResponseDTO;
import com.ycyw.api.dto.UserDTO;
import com.ycyw.api.model.AppUser;
import com.ycyw.api.model.Conversation;
import com.ycyw.api.repository.ConversationRepository;
import com.ycyw.api.repository.UserRepository;

@Service
public class ConversationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public List<ConversationResponseDTO> getConversations(String userEmail) {
        AppUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Conversation> conversations = conversationRepository.findByCustomerIdOrSupportId(user.getId(),
                user.getId());

        return conversations.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ConversationResponseDTO createConversation(String customerEmail) {
        AppUser customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<AppUser> supportUsers = userRepository.findBySupport(true);
        if (supportUsers.isEmpty()) {
            throw new RuntimeException("No support users available");
        }
        int randomIndex = (int) (Math.random() * supportUsers.size());
        AppUser support = supportUsers.get(randomIndex);

        Conversation conversation = new Conversation();
        conversation.setCustomer(customer);
        conversation.setSupport(support);

        Conversation savedConversation = conversationRepository.save(conversation);
        ConversationResponseDTO conversationResponseDTO = convertToDTO(savedConversation);

        simpMessagingTemplate.convertAndSend("/chat/conversations", conversationResponseDTO);

        return conversationResponseDTO;
    }

    private ConversationResponseDTO convertToDTO(Conversation conversation) {
        ConversationResponseDTO conversationResponseDTO = new ConversationResponseDTO();
        conversationResponseDTO.setId(conversation.getId());

        UserDTO customerDTO = new UserDTO();
        customerDTO.setId(conversation.getCustomer().getId());
        customerDTO.setName(conversation.getCustomer().getFirstname());

        conversationResponseDTO.setCustomer(customerDTO);

        UserDTO supportDTO = new UserDTO();
        supportDTO.setId(conversation.getSupport().getId());
        supportDTO.setName(conversation.getSupport().getFirstname());

        conversationResponseDTO.setSupport(supportDTO);

        return conversationResponseDTO;
    }
}