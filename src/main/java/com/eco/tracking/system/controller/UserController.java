package com.eco.tracking.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eco.tracking.system.dto.UserDTO;

import com.eco.tracking.system.request.UserUpdateRequest;
import com.eco.tracking.system.request.UserUpdatePasswordRequest;

import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.service.UserService;

import com.eco.tracking.system.exception.ExceptionType.UniqueException;
import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;
import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class UserController
{
    private final UserService userService;

    @PostMapping("/user/{id}")
    public ResponseEntity<GenericResponse> updateUser(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        UserUpdateRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException,
        UniqueException
    {
        return ResponseEntity.ok(
            userService.updateUser(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/update-user-password")
    public ResponseEntity<GenericResponse> updateUserPassword(
        @Valid
        @RequestBody
        UserUpdatePasswordRequest userUpdatePasswordRequest,
        BindingResult result,
        @RequestHeader(name="Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            userService.updateUserPassword(
                userUpdatePasswordRequest,
                result,
                token.substring(7)
            )
        );
    }

    @GetMapping("user")
    public ResponseEntity<RecordResponse<UserDTO>> getUser(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(
          userService.getUser(token.substring(7))
        );
    }

    @PostMapping("admin/user-disable/{id}")
    public ResponseEntity<GenericResponse> disableUser(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(
            userService.userDisable(id)
        );
    }

    @PostMapping("user-disable")
    public ResponseEntity<GenericResponse> disableUser(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(
            userService.userDisable(token)
        );
    }
}
