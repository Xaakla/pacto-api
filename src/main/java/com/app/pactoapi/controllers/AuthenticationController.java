package com.app.pactoapi.controllers;

import com.app.pactoapi.commons.ResponseResult;
import com.app.pactoapi.dtos.auth.LoginDto;
import com.app.pactoapi.dtos.auth.RegisterDto;
import com.app.pactoapi.dtos.auth.UserDto;
import com.app.pactoapi.Routes;
import com.app.pactoapi.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(Routes.Public.Auth.Register.path)
    public ResponseResult<UserDto> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseResult.success(new UserDto(authenticationService.registerUser(registerDto)));
    }

    @PostMapping(Routes.Public.Auth.Login.path)
    public ResponseResult<UserDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
        return ResponseResult.success(new UserDto(authenticationService.loginUser(loginDto)));
    }

    @GetMapping(Routes.Public.Auth.Logout.path)
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
}
