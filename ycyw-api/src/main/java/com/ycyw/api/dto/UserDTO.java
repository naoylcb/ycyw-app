package com.ycyw.api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotNull
    private Long id;

    @NotEmpty
    private String name;
}
