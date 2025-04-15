package com.ycyw.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateMessageDTO {
    @NotNull
    private Long conversationId;

    @NotEmpty
    private String content;
}