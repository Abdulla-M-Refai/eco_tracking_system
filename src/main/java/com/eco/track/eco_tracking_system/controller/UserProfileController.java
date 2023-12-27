package com.eco.track.eco_tracking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import com.eco.track.eco_tracking_system.dto.UserProfileDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;
import com.eco.track.eco_tracking_system.request.UserProfileRequest;

import com.eco.track.eco_tracking_system.service.UserProfileService;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

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
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            userProfileService.updateProfile(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @GetMapping("/all-profiles")
    public ResponseEntity<RecordResponse<List<UserProfileDTO>>> getAllProfiles()
    {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }

    @GetMapping("/profiles")
    public ResponseEntity<RecordResponse<List<UserProfileDTO>>> getProfiles(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(userProfileService.getProfiles(token.substring(7)));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<RecordResponse<UserProfileDTO>> getProfile(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<GenericResponse> deleteProfile(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            userProfileService.deleteProfile(
                id,
                token.substring(7)
            )
        );
    }

    @PostMapping("/profile/follow/{id}")
    public ResponseEntity<GenericResponse> followProfile(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(
            userProfileService.followProfile(
                id,
                token.substring(7)
            )
        );
    }

    @PostMapping("/profile/unfollow/{id}")
    public ResponseEntity<GenericResponse> unfollowProfile(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(
            userProfileService.unfollowProfile(
                id,
                token.substring(7)
            )
        );
    }
}
