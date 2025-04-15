package com.ycyw.api.dto;

import java.time.LocalDateTime;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class MessageResponseDTO {
    @NotNull
    private Long id;

    @NotNull
    private Long conversation;

    @NotNull
    private Long author;

    @NotEmpty
    private String content;

    @NotNull
    private LocalDateTime createdAt;
}