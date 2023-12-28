package com.eco.track.eco_tracking_system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.config.security.JwtService;

import java.util.List;
import java.util.Objects;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.IOException;

import com.eco.track.eco_tracking_system.entity.CommunityReport;
import com.eco.track.eco_tracking_system.entity.CommunityReportRate;

import com.eco.track.eco_tracking_system.repository.UserRepository;
import com.eco.track.eco_tracking_system.repository.TopicRepository;
import com.eco.track.eco_tracking_system.repository.CommunityReportRepository;
import com.eco.track.eco_tracking_system.repository.CommunityReportRateRepository;

import com.eco.track.eco_tracking_system.dto.CommunityReportDTO;

import com.eco.track.eco_tracking_system.response.RecordResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;

import com.eco.track.eco_tracking_system.request.RateRequest;
import com.eco.track.eco_tracking_system.request.CommunityReportRequest;

import com.eco.track.eco_tracking_system.util.Helper;

import com.eco.track.eco_tracking_system.exception.ExceptionType.UniqueException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class CommunityReportService
{
    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final CommunityReportRepository communityReportRepository;

    private final CommunityReportRateRepository communityReportRateRepository;

    private final JwtService jwtService;

    @Value(value = "${reports_directory_name}")
    private String uploadDirectoryName;

    @Value(value = "${reports_directory_path}")
    private String uploadDirectory;

    @Value(value = "${reports_target_directory_path}")
    private String targetUploadDirectory;

    public GenericResponse createCommunityReport(
        CommunityReportRequest request,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException,
        IOException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var topic = topicRepository.findById(request.getParsedTopicID())
            .orElseThrow(() -> new NotFoundException("topic not found"));

        String reportURI = Helper.uploadFile(
            request.getReport(),
            uploadDirectoryName,
            uploadDirectory,
            targetUploadDirectory
        );

        var communityReport = CommunityReport
            .builder()
            .user(user)
            .topic(topic)
            .report(reportURI)
            .rate(0f)
            .build();

        communityReportRepository.save(communityReport);

        return GenericResponse
            .builder()
            .state("success")
            .message("report created successfully")
            .build();
    }

    @Transactional
    public GenericResponse updateCommunityReport(
        long id,
        CommunityReportRequest request,
        BindingResult result,
        String token
    ) throws
        ValidationException,
        NotFoundException,
        UnauthorizedException,
        IOException
    {
        Helper.fieldsValidate(result);

        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var topic = topicRepository.findById(request.getParsedTopicID())
            .orElseThrow(() -> new NotFoundException("topic not found"));

        var report = communityReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("report not found"));

        if(!Objects.equals(report.getUser().getUserID(), user.getUserID()))
            throw new UnauthorizedException("unauthorized user");

        String oldReport = report.getReport();
        String oldFileName = oldReport.split("/")[oldReport.split("/").length - 1];

        Path fileToDeletePath = Paths.get(uploadDirectory, oldFileName);
        Files.deleteIfExists(fileToDeletePath);

        String reportURI = Helper.uploadFile(
            request.getReport(),
            uploadDirectoryName,
            uploadDirectory,
            targetUploadDirectory
        );

        report.setTopic(topic);
        report.setReport(reportURI);

        communityReportRepository.save(report);

        return GenericResponse
            .builder()
            .state("success")
            .message(reportURI)
            .build();
    }

    public RecordResponse<List<CommunityReportDTO>> getAllCommunityReports()
    {
        List<CommunityReportDTO> communityReportDTOS = communityReportRepository.findAll()
            .stream()
            .map(report ->
                CommunityReportDTO
                    .builder()
                    .reportID(report.getReportId())
                    .topicID(report.getTopic().getTopicID())
                    .report(report.getReport())
                    .rate(report.getRate())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<CommunityReportDTO>>builder()
            .data(communityReportDTOS)
            .build();
    }

    public RecordResponse<List<CommunityReportDTO>> getCommunityReports(
        String token
    ) throws NotFoundException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        List<CommunityReportDTO> communityReportDTOS = user.getCommunityReports()
            .stream()
            .map(report ->
                CommunityReportDTO
                    .builder()
                    .reportID(report.getReportId())
                    .topicID(report.getTopic().getTopicID())
                    .report(report.getReport())
                    .rate(report.getRate())
                    .build()
            )
            .toList();

        return RecordResponse
            .<List<CommunityReportDTO>>builder()
            .data(communityReportDTOS)
            .build();
    }

    public RecordResponse<CommunityReportDTO> getCommunityReport(
        long id
    ) throws NotFoundException
    {
        var report = communityReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("report not found"));

        var reportDTO = CommunityReportDTO
            .builder()
            .reportID(report.getReportId())
            .topicID(report.getTopic().getTopicID())
            .report(report.getReport())
            .rate(report.getRate())
            .build();

        return RecordResponse
            .<CommunityReportDTO>builder()
            .data(reportDTO)
            .build();
    }

    @Transactional
    public GenericResponse deleteCommunityReport(
        long id,
        String token
    ) throws
        NotFoundException,
        UnauthorizedException
    {
        String username = jwtService.extractUsername(token);

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("user not found"));

        var report = communityReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("report not found"));

        if(!Objects.equals(report.getUser().getUserID(), user.getUserID()))
            throw new UnauthorizedException("unauthorized user");

        communityReportRepository.delete(report);

        return GenericResponse
            .builder()
            .state("success")
            .message("report deleted successfully")
            .build();
    }

    @Transactional
    public GenericResponse rateCommunityReport(
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

        var report = communityReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("report not found"));

        int size = report.getCommunityReportRates()
            .stream()
            .filter(data -> Objects.equals(data.getUser().getUserID(), user.getUserID()))
            .toList()
            .size();

        if(size != 0)
            throw new UniqueException("user already rated this data");

        var rate = CommunityReportRate
            .builder()
            .user(user)
            .communityReport(report)
            .rate(request.getParsedRate())
            .build();

        communityReportRateRepository.save(rate);
        report.getCommunityReportRates().add(rate);

        double reportRateSum = report.getCommunityReportRates()
            .stream()
            .mapToDouble(CommunityReportRate::getRate)
            .sum();

        float newReportRate = (float) reportRateSum / report.getCommunityReportRates().size();

        report.setRate(newReportRate);
        communityReportRepository.save(report);

        var ratedUser = report.getUser();

        double userReportsRateSum = ratedUser.getCommunityReports()
            .stream()
            .mapToDouble(CommunityReport::getRate)
            .sum();

        float newUserReportsRate = (float) userReportsRateSum / ratedUser.getCommunityReports().size();
        ratedUser.setCommunityReportRate(newUserReportsRate);

        userRepository.save(ratedUser);

        return GenericResponse
            .builder()
            .state("success")
            .message("report rated successfully")
            .build();
    }
}