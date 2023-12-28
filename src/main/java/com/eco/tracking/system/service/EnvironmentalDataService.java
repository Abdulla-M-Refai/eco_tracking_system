package com.eco.tracking.system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

import jakarta.mail.MessagingException;

import com.eco.tracking.system.entity.EnvironmentalData;
import com.eco.tracking.system.entity.EnvironmentalDataRate;

import com.eco.tracking.system.config.security.JwtService;

import com.eco.tracking.system.repository.UserRepository;
import com.eco.tracking.system.repository.UserProfileRepository;
import com.eco.tracking.system.repository.EnvironmentalDataRepository;
import com.eco.tracking.system.repository.EnvironmentalDataRateRepository;

import com.eco.tracking.system.dto.EnvironmentalDataDTO;

import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.request.RateRequest;
import com.eco.tracking.system.request.EnvironmentalDataRequest;

import com.eco.tracking.system.util.Helper;

import com.eco.tracking.system.exception.ExceptionType.UniqueException;
import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;
import com.eco.tracking.system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class EnvironmentalDataService
{
    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final EnvironmentalDataRepository environmentalDataRepository;

    private final EnvironmentalDataRateRepository environmentalDataRateRepository;

    private final JwtService jwtService;

    private final EmailService emailService;

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

        new Thread(() ->
            profile.getFollower()
                .forEach(follower ->
                {
                    if(follower.getThreshold() <= environmentalData.getValue())
                    {
                        try
                        {
                            String subject = "Significant Change in " + environmentalData.getDataType();

                            String body =
                                "data source: " + environmentalData.getDataSource() + "\n" +
                                "data type: " + environmentalData.getDataType() + "\n" +
                                "value: " + environmentalData.getValue() + "\n" +
                                "unit: " + environmentalData.getUnit() + "\n" +
                                "time: " + environmentalData.getTime() + "\n" +
                                "latitude: " + environmentalData.getLatitude() + "\n" +
                                "longitude: " + environmentalData.getLongitude() + "\n" +
                                "profile: " + profile.getFullName();

                            emailService.notifyUser(
                                follower.getUser().getEmail(),
                                subject,
                                body
                            );
                        }
                        catch (MessagingException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                })).start();

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
        environmentalData.getEnvironmentalDataRates().add(rate);

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
