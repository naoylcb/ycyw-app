package com.ycyw.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class AuthRequestDTO {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}