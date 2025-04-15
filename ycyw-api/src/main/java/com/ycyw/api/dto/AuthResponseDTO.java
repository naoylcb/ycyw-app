package com.ycyw.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class AuthResponseDTO {
    @NotEmpty
    private String token;

    @NotNull
    private Long userId;
}