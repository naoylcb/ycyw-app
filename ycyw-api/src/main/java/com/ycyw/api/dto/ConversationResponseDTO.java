package com.ycyw.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class ConversationResponseDTO {
    @NotNull
    private Long id;

    private UserDTO customer;

    private UserDTO support;
}