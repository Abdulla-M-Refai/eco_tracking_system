package com.eco.tracking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.eco.tracking.system.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>
{
    Optional<Topic> findByType(String type);
}
