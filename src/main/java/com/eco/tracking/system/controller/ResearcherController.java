package com.eco.tracking.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.eco.tracking.system.service.ResearcherService;

import com.eco.tracking.system.request.AirPollutionRequest;
import com.eco.tracking.system.request.SolarIrradianceRequest;

import com.eco.tracking.system.exception.ExceptionType.ValidationException;

@RestController
@RequestMapping("/api/researchers")
@RequiredArgsConstructor
public class ResearcherController
{
    private final ResearcherService researcherService;

    @PostMapping("/air-pollution")
    public ResponseEntity<String> getAirPollution(
        @Valid
        @RequestBody
        AirPollutionRequest request,
        BindingResult result
    ) throws ValidationException
    {
        return ResponseEntity
            .ok()
            .header("Content-Type", "application/json")
            .body(researcherService.getAirPollution(request, result));
    }

    @PostMapping("/solar-irradiance")
    public ResponseEntity<String> getSolarIrradiance(
        @Valid
        @RequestBody
        SolarIrradianceRequest request,
        BindingResult result
    ) throws ValidationException
    {
        return ResponseEntity
            .ok()
            .header("Content-Type", "application/json")
            .body(researcherService.getSolarIrradiance(request, result));
    }
}
