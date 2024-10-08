package com.app.pactoapi.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@NotBlank(message = "A username is required to register.") String username,
                          @NotBlank(message = "An email is required to register.") String email,
                          @NotBlank(message = "A password is required to register.") String password,
                          @NotBlank(message = "Password confirmation required to register.") String confirmPassword) {
}
