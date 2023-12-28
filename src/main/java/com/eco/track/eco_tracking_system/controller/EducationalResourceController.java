package com.eco.track.eco_tracking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import com.eco.track.eco_tracking_system.dto.EducationalResourceDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;
import com.eco.track.eco_tracking_system.request.EducationalResourceRequest;

import com.eco.track.eco_tracking_system.service.EducationalResourceService;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class EducationalResourceController
{
    private final EducationalResourceService educationalResourceService;

    @PostMapping("/educational-resource")
    public ResponseEntity<GenericResponse> createEducationalResource(
        @Valid
        @RequestBody
        EducationalResourceRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException
    {
        return ResponseEntity.ok(
            educationalResourceService.createEducationalResource(
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/educational-resource/{id}")
    public ResponseEntity<GenericResponse> updateEducationalResource(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        EducationalResourceRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            educationalResourceService.updateEducationalResource(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @GetMapping("/all-educational-resources")
    public ResponseEntity<RecordResponse<List<EducationalResourceDTO>>> getAllEducationalResource() throws NotFoundException
    {
        return ResponseEntity.ok(educationalResourceService.getAllEducationalResource());
    }

    @GetMapping("/educational-resources")
    public ResponseEntity<RecordResponse<List<EducationalResourceDTO>>> getEducationalResources(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(educationalResourceService.getEducationalResources(token.substring(7)));
    }

    @GetMapping("/educational-resource/{id}")
    public ResponseEntity<RecordResponse<EducationalResourceDTO>> getEducationalResource(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(educationalResourceService.getEducationalResource(id));
    }

    @DeleteMapping("/educational-resource/{id}")
    public ResponseEntity<GenericResponse> deleteEducationalResource(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            educationalResourceService.deleteEducationalResource(
                id,
                token.substring(7)
            )
        );
    }
}