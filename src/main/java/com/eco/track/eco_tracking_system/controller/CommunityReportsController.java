package com.eco.track.eco_tracking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import java.io.IOException;

import com.eco.track.eco_tracking_system.dto.CommunityReportDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;
import com.eco.track.eco_tracking_system.request.CommunityReportRequest;

import com.eco.track.eco_tracking_system.service.CommunityReportService;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class CommunityReportsController
{
    private final CommunityReportService communityReportService;

    @PostMapping("/community-report")
    public ResponseEntity<GenericResponse> createCommunityReport(
        @Valid
        @ModelAttribute
        CommunityReportRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
        IOException
    {
        return ResponseEntity.ok(
            communityReportService.createCommunityReport(
                request,
                result,
                token.substring(7)
            )
        );
    }

    @PostMapping("/community-report/{id}")
    public ResponseEntity<GenericResponse> updateCommunityReport(
        @PathVariable
        long id,
        @Valid
        @ModelAttribute
        CommunityReportRequest request,
        BindingResult result,
        @RequestHeader("Authorization")
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException,
        IOException
    {
        return ResponseEntity.ok(
            communityReportService.updateCommunityReport(
                id,
                request,
                result,
                token.substring(7)
            )
        );
    }

    @GetMapping("/all-community-reports")
    public ResponseEntity<RecordResponse<List<CommunityReportDTO>>> getCommunityReports() throws NotFoundException
    {
        return ResponseEntity.ok(communityReportService.getAllCommunityReports());
    }

    @GetMapping("/community-reports")
    public ResponseEntity<RecordResponse<List<CommunityReportDTO>>> getCommunityReports(
        @RequestHeader("Authorization")
        String token
    ) throws NotFoundException
    {
        return ResponseEntity.ok(communityReportService.getCommunityReports(token.substring(7)));
    }

    @GetMapping("/community-report/{id}")
    public ResponseEntity<RecordResponse<CommunityReportDTO>> getEnvironmentalData(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(communityReportService.getCommunityReport(id));
    }

    @DeleteMapping("/community-report/{id}")
    public ResponseEntity<GenericResponse> deleteCommunityReport(
        @PathVariable
        long id,
        @RequestHeader("Authorization")
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        return ResponseEntity.ok(
            communityReportService.deleteCommunityReport(
                id,
                token.substring(7)
            )
        );
    }
}
