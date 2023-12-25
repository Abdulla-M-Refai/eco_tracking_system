package com.eco.track.eco_tracking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.response.GenericResponse;
import com.eco.track.eco_tracking_system.request.UserProfileRequest;
import com.eco.track.eco_tracking_system.response.UserProfileResponse;
import com.eco.track.eco_tracking_system.response.UserProfilesResponse;

import com.eco.track.eco_tracking_system.service.UserProfileService;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class UserProfileController
{
    private final UserProfileService userProfileService;

    @PostMapping("/profile")
    public ResponseEntity<GenericResponse> createProfile(
        @Valid
        @RequestBody
        UserProfileRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            userProfileService.createProfile(
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/profile/{id}")
    public ResponseEntity<GenericResponse> updateProfile(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        UserProfileRequest request,
        BindingResult result
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            userProfileService.updateProfile(
                id,
                request,
                result
            )
        );
    }

    @GetMapping("/profiles")
    public ResponseEntity<UserProfilesResponse> getProfiles(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(userProfileService.getProfiles(token.substring(7)));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileResponse> getProfile(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<GenericResponse> deleteProfile(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(userProfileService.deleteProfile(id));
    }
}
