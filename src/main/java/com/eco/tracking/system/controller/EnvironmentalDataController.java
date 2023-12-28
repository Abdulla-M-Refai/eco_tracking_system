package com.eco.tracking.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import com.eco.tracking.system.dto.EnvironmentalDataDTO;
import com.eco.tracking.system.service.EnvironmentalDataService;

import com.eco.tracking.system.request.RateRequest;
import com.eco.tracking.system.request.EnvironmentalDataRequest;

import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.exception.ExceptionType.UniqueException;
import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;
import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class EnvironmentalDataController
{
    private final EnvironmentalDataService environmentalDataService;

    @PostMapping("/environmental-data")
    public ResponseEntity<GenericResponse> createEnvironmentalData(
        @Valid
        @RequestBody
        EnvironmentalDataRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
            ValidationException,
            NotFoundException,
            UnauthorizedException
    {
        return ResponseEntity.ok(
            environmentalDataService.createEnvironmentalData(
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/environmental-data/{id}")
    public ResponseEntity<GenericResponse> updateEnvironmentalData(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        EnvironmentalDataRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            environmentalDataService.updateEnvironmentalData(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/environmental-data-rate/{id}")
    public ResponseEntity<GenericResponse> rateEnvironmentalData(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        RateRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
            UniqueException
    {
        return ResponseEntity.ok(
            environmentalDataService.rateEnvironmentalData(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @GetMapping("/profile-environmental-data/{id}")
    public ResponseEntity<RecordResponse<List<EnvironmentalDataDTO>>> getProfileEnvironmentalData(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(environmentalDataService.getProfileEnvironmentalData(id));
    }

    @GetMapping("/environmental-data/{id}")
    public ResponseEntity<RecordResponse<EnvironmentalDataDTO>> getEnvironmentalData(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(environmentalDataService.getEnvironmentalData(id));
    }

    @DeleteMapping("/environmental-data/{id}")
    public ResponseEntity<GenericResponse> deleteEnvironmentalData(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            environmentalDataService.deleteEnvironmentalData(
                id,
                token.substring(7)
            )
        );
    }
}
