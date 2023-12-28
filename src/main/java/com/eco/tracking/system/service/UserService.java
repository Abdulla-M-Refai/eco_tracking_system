package com.eco.tracking.system.service;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import com.eco.tracking.system.config.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eco.tracking.system.dto.UserDTO;
import com.eco.tracking.system.repository.UserRepository;

import com.eco.tracking.system.request.UserUpdateRequest;
import com.eco.tracking.system.request.UserUpdatePasswordRequest;

import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.util.Helper;

import com.eco.tracking.system.exception.ExceptionType.UniqueException;
import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;
import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public GenericResponse updateUser(
        long id,
        UserUpdateRequest request,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException,
        UniqueException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        if(user.getUserID() != id)
            throw new UnauthorizedException("unauthorized user");

        if(
            userRepository.findByEmail(request.getEmail()).isPresent() &&
            !user.getEmail().equalsIgnoreCase(request.getEmail())
        ) throw new UniqueException("email already exists");

        if(
            userRepository.findByUsername(request.getUsername()).isPresent() &&
            !user.getUsername().equalsIgnoreCase(request.getUsername())
        ) throw new UniqueException("username already exists");

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        userRepository.save(user);

        return GenericResponse
            .builder()
            .state("success")
            .message("user updated successfully")
            .build();
    }

    @Transactional
    public GenericResponse updateUserPassword(
        UserUpdatePasswordRequest userUpdatePasswordRequest,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        if(!passwordEncoder
            .matches(
                userUpdatePasswordRequest.getOldPassword(),
                user.getPassword()
            )
        ) throw new NotFoundException("user not found");

        user.setPassword(passwordEncoder.encode(userUpdatePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return GenericResponse
            .builder()
            .state("success")
            .message("password updated successfully")
            .build();
    }

    public RecordResponse<UserDTO> getUser(
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var userDTO = UserDTO
            .builder()
            .id(user.getUserID())
            .fullName(user.getFullName())
            .username(user.getUsername())
            .email(user.getEmail())
            .communityReportRate(user.getCommunityReportRate())
            .isEnabled(user.getIsEnabled())
            .build();

        return RecordResponse
            .<UserDTO>builder()
            .data(userDTO)
            .build();
    }

    @Transactional
    public GenericResponse userDisable(
        long id
    ) throws NotFoundException
    {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("user not found"));

        user.setIsEnabled(false);
        userRepository.save(user);

        return GenericResponse
            .builder()
            .state("success")
            .message("user disabled successfully")
            .build();
    }

    public GenericResponse userDisable(
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        user.setIsEnabled(false);
        userRepository.save(user);

        return GenericResponse
            .builder()
            .state("success")
            .message("user disabled successfully")
            .build();
    }
}
