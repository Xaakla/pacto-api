package com.app.pactoapi.services;

import com.app.pactoapi.database.entities.User;
import com.app.pactoapi.dtos.auth.LoginDto;
import com.app.pactoapi.dtos.auth.RegisterDto;
import com.app.pactoapi.exceptions.errors.BadRequestException;
import com.app.pactoapi.commons.Utils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User registerUser(RegisterDto registerDto) {

        if (!Objects.equals(registerDto.password(), registerDto.confirmPassword())) {
            throw new BadRequestException("the passwords are different");
        }

        if (userService.existsByEmail(registerDto.email())) {
            throw new BadRequestException("the email is already in use");
        }

        return userService.save(new User(registerDto.username(), registerDto.email(), passwordEncoder.encode(registerDto.password())));
    }

    public User loginUser(LoginDto loginDto) {

        var user = userService.findByEmail(loginDto.email())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password"));

        if (!new BCryptPasswordEncoder().matches(loginDto.password(), user.getPassword()))
            throw new BadRequestException("Invalid email or password!");

        var authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(loginDto.email(), loginDto.password());

        var authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(securityContext);

        return Utils.loggedUser();
    }
}
