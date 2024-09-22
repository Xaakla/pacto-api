package com.app.pactoapi.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank(message = "An email is required to login.") String email,
                       @NotBlank(message = "A password is required to login.") String password) {
}
