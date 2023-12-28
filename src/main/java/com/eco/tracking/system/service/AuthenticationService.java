package com.eco.tracking.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.BindingResult;

import org.springframework.security.authentication.DisabledException;

import com.eco.tracking.system.entity.User;
import com.eco.tracking.system.entity.Enum.UserType;

import com.eco.tracking.system.repository.UserRepository;

import com.eco.tracking.system.request.UserRegisterRequest;
import com.eco.tracking.system.request.AuthenticationRequest;

import com.eco.tracking.system.response.TokenResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.util.Helper;
import com.eco.tracking.system.config.security.JwtService;

import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value(value = "${token.expiration.time}")
    private long userTokenLifeTime;

    @Value(value = "${refresh.token.expiration.time}")
    private long userRefreshTokenLifeTime;

    public GenericResponse registerUser(
        UserRegisterRequest request,
        BindingResult result
    ) throws ValidationException
    {
        Helper.fieldsValidate(result);

        var user = User
            .builder()
            .fullName(request.getFullName())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .communityReportRate(0f)
            .type(UserType.USER)
            .isEnabled(true)
            .build();

        userRepository.save(user);

        return GenericResponse
            .builder()
            .state("success")
            .message("user registered successfully")
            .build();
    }

    public TokenResponse authenticate(
        AuthenticationRequest request,
        BindingResult result
    ) throws
        ValidationException,
            NotFoundException
    {
        Helper.fieldsValidate(result);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("user not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new NotFoundException("user not found");

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        var token = jwtService.generateToken(user, userTokenLifeTime);
        var refreshToken = jwtService.generateToken(user, userRefreshTokenLifeTime);

        return TokenResponse
            .builder()
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }

    public TokenResponse refreshToken(
        String refreshToken
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(refreshToken);

        var user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        if(!user.getIsEnabled())
            throw new DisabledException("user is disabled");

        var token = jwtService.generateToken(user, userTokenLifeTime);

        if(userRefreshTokenLifeTime - jwtService.extractExpiration(refreshToken).getTime() < 86400000)
            refreshToken = jwtService.generateToken(user, userRefreshTokenLifeTime);

        return TokenResponse
            .builder()
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }
}