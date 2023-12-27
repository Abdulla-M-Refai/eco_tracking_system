package com.eco.track.eco_tracking_system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.config.security.JwtService;

import java.util.List;
import java.util.Objects;

import com.eco.track.eco_tracking_system.entity.EnvironmentalData;
import com.eco.track.eco_tracking_system.entity.EnvironmentalDataRate;

import com.eco.track.eco_tracking_system.repository.UserRepository;
import com.eco.track.eco_tracking_system.repository.UserProfileRepository;
import com.eco.track.eco_tracking_system.repository.EnvironmentalDataRepository;
import com.eco.track.eco_tracking_system.repository.EnvironmentalDataRateRepository;

import com.eco.track.eco_tracking_system.dto.EnvironmentalDataDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;

import com.eco.track.eco_tracking_system.request.RateRequest;
import com.eco.track.eco_tracking_system.request.EnvironmentalDataRequest;

import com.eco.track.eco_tracking_system.util.Helper;

import com.eco.track.eco_tracking_system.exception.ExceptionType.UniqueException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class EnvironmentalDataService
{
    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final EnvironmentalDataRepository environmentalDataRepository;

    private final EnvironmentalDataRateRepository environmentalDataRateRepository;

    private final JwtService jwtService;

    public GenericResponse createEnvironmentalData(
        EnvironmentalDataRequest request,
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

        var profile = userProfileRepository.findById(request.getParsedProfileID())
            .orElseThrow(() -> new NotFoundException("profile not found"));

        int size = user.getUserProfiles()
            .stream()
            .filter(userProfile -> Objects.equals(userProfile.getProfileID(), profile.getProfileID()))
            .toList().size();

        if(size == 0)
            throw new UnauthorizedException("unauthorized user");

        var environmentalData = EnvironmentalData
            .builder()
            .dataSource(request.getDataSource())
            .dataType(request.getDataType())
            .value(request.getParsedValue())
            .unit(request.getUnit())
            .time(request.getTime())
            .latitude(request.getParsedLatitude())
            .longitude(request.getParsedLongitude())
            .userProfile(profile)
            .rate(0f)
            .build();

        environmentalDataRepository.save(environmentalData);

        return GenericResponse
            .builder()
            .state("success")
            .message("Environmental data created successfully")
            .build();
    }

    @Transactional
    public GenericResponse updateEnvironmentalData(
        long id,
        EnvironmentalDataRequest request,
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

        var profile = userProfileRepository.findById(request.getParsedProfileID())
            .orElseThrow(() -> new NotFoundException("profile not found"));

        var environmentalData = environmentalDataRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Environmental data not found"));

        if(!Objects.equals(environmentalData.getUserProfile().getUser().getUserID(), user.getUserID()))
               throw new UnauthorizedException("unauthorized user");

        environmentalData.setDataType(request.getDataType());
        environmentalData.setDataSource(request.getDataSource());
        environmentalData.setTime(request.getTime());
        environmentalData.setUnit(request.getUnit());
        environmentalData.setLongitude(request.getParsedLongitude());
        environmentalData.setLatitude(request.getParsedLatitude());
        environmentalData.setUserProfile(profile);
        environmentalData.setValue(request.getParsedValue());

        environmentalDataRepository.save(environmentalData);

        return GenericResponse
            .builder()
            .state("success")
            .message("Environmental data updated successfully")
            .build();
    }

    public RecordResponse<List<EnvironmentalDataDTO>> getProfileEnvironmentalData(
        long profileID
    ) throws NotFoundException
    {
        var profile = userProfileRepository.findById(profileID)
            .orElseThrow(() -> new NotFoundException("profile not found"));

        List<EnvironmentalDataDTO> environmentalDataDTOS = profile.getEnvironmentalDataList()
            .stream()
            .map(environmentalData ->
                EnvironmentalDataDTO
                    .builder()
                    .profileID(environmentalData.getUserProfile().getProfileID())
                    .dataSource(environmentalData.getDataSource())
                    .dataType(environmentalData.getDataType())
                    .latitude(environmentalData.getLatitude())
                    .longitude(environmentalData.getLongitude())
                    .unit(environmentalData.getUnit())
                    .value(environmentalData.getValue())
                    .time(environmentalData.getTime())
                    .rate(environmentalData.getRate())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<EnvironmentalDataDTO>>builder()
            .data(environmentalDataDTOS)
            .build();
    }

    public RecordResponse<EnvironmentalDataDTO> getEnvironmentalData(
        long id
    ) throws NotFoundException
    {
        var environmentalData = environmentalDataRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("environmental data not found"));

        var environmentalDataDTO = EnvironmentalDataDTO
            .builder()
            .profileID(environmentalData.getUserProfile().getProfileID())
            .dataSource(environmentalData.getDataSource())
            .dataType(environmentalData.getDataType())
            .latitude(environmentalData.getLatitude())
            .longitude(environmentalData.getLongitude())
            .unit(environmentalData.getUnit())
            .value(environmentalData.getValue())
            .time(environmentalData.getTime())
            .rate(environmentalData.getRate())
            .build();

        return RecordResponse
            .<EnvironmentalDataDTO>builder()
            .data(environmentalDataDTO)
            .build();
    }

    @Transactional
    public GenericResponse deleteEnvironmentalData(
        long id,
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var environmentalData = environmentalDataRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("environmental data not found"));

        if(!Objects.equals(environmentalData.getUserProfile().getUser().getUserID(), user.getUserID()))
            throw new UnauthorizedException("unauthorized user");

        environmentalDataRepository.delete(environmentalData);

        return GenericResponse
            .builder()
            .state("success")
            .message("Environmental data deleted successfully")
            .build();
    }

    @Transactional
    public GenericResponse rateEnvironmentalData(
        long id,
        RateRequest request,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UniqueException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var environmentalData = environmentalDataRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("environmental data not found"));

        int size = environmentalData.getEnvironmentalDataRates()
            .stream()
            .filter(data -> Objects.equals(data.getUser().getUserID(), user.getUserID()))
            .toList()
            .size();

        if(size != 0)
            throw new UniqueException("user already rated this data");

        var rate = EnvironmentalDataRate
            .builder()
            .user(user)
            .environmentalData(environmentalData)
            .rate(request.getParsedRate())
            .build();

        environmentalDataRateRepository.save(rate);

        double dataRateSum = environmentalData.getEnvironmentalDataRates()
            .stream()
            .mapToDouble(EnvironmentalDataRate::getRate)
            .sum();

        float newDataRate = (float) dataRateSum / environmentalData.getEnvironmentalDataRates().size();

        environmentalData.setRate(newDataRate);
        environmentalDataRepository.save(environmentalData);

        var profile = environmentalData.getUserProfile();

        double profileRateSum = profile.getEnvironmentalDataList()
            .stream()
            .mapToDouble(EnvironmentalData::getRate)
            .sum();

        float newProfileRate = (float) profileRateSum / environmentalData.getUserProfile().getEnvironmentalDataList().size();
        profile.setProfileRate(newProfileRate);

        userProfileRepository.save(profile);

        return GenericResponse
            .builder()
            .state("success")
            .message("data rated successfully")
            .build();
    }
}
