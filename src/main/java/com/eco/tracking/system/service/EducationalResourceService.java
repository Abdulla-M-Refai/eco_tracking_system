package com.eco.tracking.system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

import com.eco.tracking.system.config.security.JwtService;

import com.eco.tracking.system.entity.EducationalResource;

import com.eco.tracking.system.repository.UserRepository;
import com.eco.tracking.system.repository.EducationalResourceRepository;

import com.eco.tracking.system.request.EducationalResourceRequest;

import com.eco.tracking.system.dto.EducationalResourceDTO;

import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.util.Helper;

import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;
import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class EducationalResourceService
{
    private final UserRepository userRepository;

    private final EducationalResourceRepository educationalResourceRepository;

    private final JwtService jwtService;

    public GenericResponse createEducationalResource(
        EducationalResourceRequest request,
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

        var educationalResource = EducationalResource
            .builder()
            .user(user)
            .title(request.getTitle())
            .url(request.getUrl())
            .build();

        educationalResourceRepository.save(educationalResource);

        return GenericResponse
            .builder()
            .state("success")
            .message("educational resource created successfully")
            .build();
    }

    @Transactional
    public GenericResponse updateEducationalResource(
        long id,
        EducationalResourceRequest request,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var educationalResource = educationalResourceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("educational resource not found"));

        if(!Objects.equals(educationalResource.getUser().getUserID(), user.getUserID()))
            throw new UnauthorizedException("unauthorized user");

        educationalResource.setUrl(request.getUrl());
        educationalResource.setTitle(request.getTitle());

        educationalResourceRepository.save(educationalResource);

        return GenericResponse
            .builder()
            .state("success")
            .message("educational resource updated successfully")
            .build();
    }

    public RecordResponse<List<EducationalResourceDTO>> getAllEducationalResource()
    {
        List<EducationalResourceDTO> educationalResourceDTOS = educationalResourceRepository.findAll()
            .stream()
            .map(resource ->
                EducationalResourceDTO
                    .builder()
                    .id(resource.getResourceId())
                    .url(resource.getUrl())
                    .title(resource.getTitle())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<EducationalResourceDTO>>builder()
            .data(educationalResourceDTOS)
            .build();
    }

    public RecordResponse<List<EducationalResourceDTO>> getEducationalResources(
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        List<EducationalResourceDTO> educationalResourceDTOS = user.getEducationalResources()
            .stream()
            .map(resource ->
                EducationalResourceDTO
                    .builder()
                    .id(resource.getResourceId())
                    .url(resource.getUrl())
                    .title(resource.getTitle())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<EducationalResourceDTO>>builder()
            .data(educationalResourceDTOS)
            .build();
    }

    public RecordResponse<EducationalResourceDTO> getEducationalResource(
        long id
    ) throws NotFoundException
    {
        var educationalResource = educationalResourceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("educational resource not found"));

        var educationalResourceDTO = EducationalResourceDTO
            .builder()
            .id(educationalResource.getResourceId())
            .title(educationalResource.getTitle())
            .url(educationalResource.getUrl())
            .build();

        return RecordResponse
            .<EducationalResourceDTO>builder()
            .data(educationalResourceDTO)
            .build();
    }

    @Transactional
    public GenericResponse deleteEducationalResource(
        long id,
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        String username = jwtService.extractUsername(token);

        var educationalResource = educationalResourceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("educational resource not found"));

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        if(!Objects.equals(educationalResource.getUser().getUserID(), user.getUserID()))
            throw new UnauthorizedException("unauthorized user");

        educationalResourceRepository.delete(educationalResource);

        return GenericResponse
            .builder()
            .state("success")
            .message("educational resource deleted successfully")
            .build();
    }
}
