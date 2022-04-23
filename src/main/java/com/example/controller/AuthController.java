package com.example.controller;

import com.example.config.jwt.JwtProvider;
import com.example.domain.User;
import com.example.dto.AuthRequestDto;
import com.example.dto.AuthResponseDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    @ApiOperation("Registration")
    public AuthResponseDto registerUser(@RequestBody AuthRequestDto request) {
        User u = new User();
        u.setPassword(request.getPassword());
        u.setLogin(request.getLogin());
        User user = userService.saveUser(u);
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponseDto(token);
    }

    @PostMapping("/sign_in")
    @ApiOperation("Authorization")
    public AuthResponseDto auth(@RequestBody AuthRequestDto request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponseDto(token);
    }
}
