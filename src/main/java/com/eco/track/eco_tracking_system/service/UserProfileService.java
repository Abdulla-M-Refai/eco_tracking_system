package com.eco.track.eco_tracking_system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.config.security.JwtService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.eco.track.eco_tracking_system.entity.UserProfile;
import com.eco.track.eco_tracking_system.entity.ProfileFollowers;

import com.eco.track.eco_tracking_system.repository.UserRepository;
import com.eco.track.eco_tracking_system.repository.TopicRepository;
import com.eco.track.eco_tracking_system.repository.UserProfileRepository;
import com.eco.track.eco_tracking_system.repository.ProfileFollowersRepository;

import com.eco.track.eco_tracking_system.dto.UserProfileDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;
import com.eco.track.eco_tracking_system.request.UserProfileRequest;

import com.eco.track.eco_tracking_system.util.Helper;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class UserProfileService
{
    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final ProfileFollowersRepository profileFollowersRepository;

    private final TopicRepository topicRepository;

    private final JwtService jwtService;

    public GenericResponse createProfile(
        UserProfileRequest request,
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

        var topic = topicRepository.findById(request.getParsedTopicID())
            .orElseThrow(() -> new NotFoundException("topic not found"));

        var userProfile = UserProfile
            .builder()
            .user(user)
            .topic(topic)
            .fullName(request.getFullName())
            .build();

        userProfileRepository.save(userProfile);

        return GenericResponse
            .builder()
            .state("success")
            .message("profile created successfully")
            .build();
    }

    @Transactional
    public GenericResponse updateProfile(
        long id,
        UserProfileRequest request,
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

        var userProfile = userProfileRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("profile not found"));

        var topic = topicRepository.findById(request.getParsedTopicID())
            .orElseThrow(() -> new NotFoundException("topic not found"));

        int size = user.getUserProfiles()
            .stream()
            .filter(profile -> Objects.equals(profile.getProfileID(), userProfile.getProfileID()))
            .toList().size();

        if(size == 0)
            throw new UnauthorizedException("unauthorized user");

        userProfile.setTopic(topic);
        userProfile.setFullName(request.getFullName());

        userProfileRepository.save(userProfile);

        return GenericResponse
            .builder()
            .state("success")
            .message("profile updated successfully")
            .build();
    }

    public RecordResponse<List<UserProfileDTO>> getAllProfiles()
    {
        List<UserProfile> userProfiles = userProfileRepository.findAll();

        List<UserProfileDTO> userProfileDTOS = userProfiles
            .stream()
            .map(profile -> UserProfileDTO
                    .builder()
                    .id(profile.getProfileID())
                    .topicID(profile.getTopic().getTopicID())
                    .fullName(profile.getFullName())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<UserProfileDTO>>builder()
            .data(userProfileDTOS)
            .build();
    }

    public RecordResponse<List<UserProfileDTO>> getProfiles(
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        List<UserProfile> userProfiles = user.getUserProfiles();

        List<UserProfileDTO> userProfileDTOS = userProfiles
            .stream()
            .map(profile -> UserProfileDTO
                .builder()
                .id(profile.getProfileID())
                .topicID(profile.getTopic().getTopicID())
                .fullName(profile.getFullName())
                .build()
            )
            .toList();

        return RecordResponse
            .<List<UserProfileDTO>>builder()
            .data(userProfileDTOS)
            .build();
    }

    public RecordResponse<UserProfileDTO> getProfile(
        long id
    ) throws NotFoundException
    {
        var profile = userProfileRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("profile not found"));

        var profileDTO = UserProfileDTO
            .builder()
            .id(profile.getProfileID())
            .topicID(profile.getTopic().getTopicID())
            .fullName(profile.getFullName())
            .build();

        return RecordResponse
            .<UserProfileDTO>builder()
            .data(profileDTO)
            .build();
    }

    @Transactional
    public GenericResponse deleteProfile(
        long id,
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var profile = userProfileRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("profile not found"));

        int size = user.getUserProfiles()
            .stream()
            .filter(userProfile -> Objects.equals(profile.getProfileID(), userProfile.getProfileID()))
            .toList().size();

        if(size == 0)
            throw new UnauthorizedException("unauthorized user");

        userProfileRepository.delete(profile);

        return GenericResponse
            .builder()
            .state("success")
            .message("profile deleted successfully")
            .build();
    }

    public GenericResponse followProfile(
        long id,
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var profile = userProfileRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("profile not found"));

        var profileFollower = profileFollowersRepository.findByUserIDAndProfileID(
                user.getUserID(),
                profile.getProfileID()
        )
        .or(
            () -> Optional.of(
                ProfileFollowers
                    .builder()
                    .user(user)
                    .userProfile(profile)
                    .build()
            )
        )
        .get();

        profileFollowersRepository.save(profileFollower);

        return GenericResponse
            .builder()
            .state("success")
            .message("profile followed successfully")
            .build();
    }

    @Transactional
    public GenericResponse unfollowProfile(
        long id,
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("user not found"));

        var profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("profile not found"));

        var profileFollower = profileFollowersRepository.findByUserIDAndProfileID(
                user.getUserID(),
                profile.getProfileID()
        ).orElseThrow(() -> new NotFoundException("no follow to remove"));

        profileFollowersRepository.delete(profileFollower);

        return GenericResponse
            .builder()
            .state("success")
            .message("profile unfollowed successfully")
            .build();
    }
}
