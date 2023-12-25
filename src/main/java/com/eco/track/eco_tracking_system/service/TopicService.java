package com.eco.track.eco_tracking_system.service;

import lombok.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.validation.BindingResult;

import com.eco.track.eco_tracking_system.entity.Topic;
import com.eco.track.eco_tracking_system.dto.TopicDTO;

import com.eco.track.eco_tracking_system.repository.TopicRepository;

import com.eco.track.eco_tracking_system.request.TopicRequest;
import com.eco.track.eco_tracking_system.response.TopicResponse;
import com.eco.track.eco_tracking_system.response.TopicsResponse;
import com.eco.track.eco_tracking_system.response.GenericResponse;

import com.eco.track.eco_tracking_system.util.Helper;

import com.eco.track.eco_tracking_system.exception.ExceptionType.NotFoundException;
import com.eco.track.eco_tracking_system.exception.ExceptionType.ValidationException;

@Service
@RequiredArgsConstructor
public class TopicService
{
    private final TopicRepository topicRepository;

    public TopicResponse getTopic(
        long id
    ) throws NotFoundException
    {
        var topic = topicRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("topic not found"));

        var topicDTO = TopicDTO
            .builder()
            .id(topic.getTopicID())
            .type(topic.getType())
            .build();

        return TopicResponse
            .builder()
            .topic(topicDTO)
            .build();
    }

    public TopicsResponse getTopics()
    {
        var topics = topicRepository.findAll();

        var topicsDTO = topics
            .stream()
            .map(topic ->
                TopicDTO
                    .builder()
                    .id(topic.getTopicID())
                    .type(topic.getType())
                    .build()
            )
            .toList();

        return TopicsResponse
            .builder()
            .topics(topicsDTO)
            .build();
    }

    @Transactional
    public GenericResponse updateTopic(
        long id,
        TopicRequest request,
        BindingResult result
    ) throws
        ValidationException,
        NotFoundException
    {
        Helper.fieldsValidate(result);

        var topic = topicRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("topic not found"));

        topic.setType(request.getType());
        topicRepository.save(topic);

        return GenericResponse
            .builder()
            .state("success")
            .message("topic updated successfully")
            .build();
    }

    public GenericResponse createTopic(
        TopicRequest request,
        BindingResult result
    ) throws ValidationException
    {
        Helper.fieldsValidate(result);

        var topic = Topic
            .builder()
            .type(request.getType())
            .build();

        topicRepository.save(topic);

        return GenericResponse
            .builder()
            .state("success")
            .message("topic created successfully")
            .build();
    }

    @Transactional
    public GenericResponse deleteTopic(
        long id
    ) throws NotFoundException
    {
        var topic = topicRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("topic not found"));

        topicRepository.delete(topic);

        return GenericResponse
            .builder()
            .state("success")
            .message("topic deleted successfully")
            .build();
    }
}
