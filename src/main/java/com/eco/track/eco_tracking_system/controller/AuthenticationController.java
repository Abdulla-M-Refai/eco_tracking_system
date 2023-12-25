package com.eco.track.eco_tracking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.request.UserRegisterRequest;
import com.eco.track.eco_tracking_system.request.AuthenticationRequest;

import com.eco.track.eco_tracking_system.response.TokenResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;

import com.eco.track.eco_tracking_system.service.AuthenticationService;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(
        @Valid
        @RequestBody
        UserRegisterRequest request,
        BindingResult result
    ) throws ValidationException
    {
        return ResponseEntity.ok(
            authenticationService.registerUser(
                request,
                result
            )
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(
        @Valid
        @RequestBody
        AuthenticationRequest request,
        BindingResult result
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            authenticationService.authenticate(
                request,
                result
            )
        );
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(
        @RequestHeader("Authorization")
        String refreshToken
    ) throws NotFoundException
    {
        refreshToken = refreshToken.substring(7);
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}