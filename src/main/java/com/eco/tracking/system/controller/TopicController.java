package com.eco.tracking.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

import com.eco.tracking.system.dto.TopicDTO;
import com.eco.tracking.system.service.TopicService;

import com.eco.tracking.system.request.TopicRequest;
import com.eco.tracking.system.response.RecordResponse;
import com.eco.tracking.system.response.GenericResponse;

import com.eco.tracking.system.exception.ExceptionType.NotFoundException;
import com.eco.tracking.system.exception.ExceptionType.ValidationException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Validated
public class TopicController
{
    private final TopicService topicService;

    @PostMapping("/admin/topic")
    public ResponseEntity<GenericResponse> createTopic(
        @Valid
        @RequestBody
        TopicRequest request,
        BindingResult result
    ) throws ValidationException
    {
        return ResponseEntity.ok(
            topicService.createTopic(
                request,
                result
            )
        );
    }

    @PostMapping("/admin/topic/{id}")
    public ResponseEntity<GenericResponse> updateTopic(
        @PathVariable
        long id,
        @Valid
        @RequestBody
        TopicRequest request,
        BindingResult result
    ) throws
        ValidationException,
            NotFoundException
    {
        return ResponseEntity.ok(
            topicService.updateTopic(
                id,
                request,
                result
            )
        );
    }

    @GetMapping("/topics")
    public ResponseEntity<RecordResponse<List<TopicDTO>>> getTopics()
    {
        return ResponseEntity.ok(topicService.getTopics());
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<RecordResponse<TopicDTO>> getTopic(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(topicService.getTopic(id));
    }

    @DeleteMapping("/admin/topic/{id}")
    public ResponseEntity<GenericResponse> deleteTopic(
        @PathVariable
        long id
    ) throws NotFoundException
    {
        return ResponseEntity.ok(topicService.deleteTopic(id));
    }
}
