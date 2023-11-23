package com.eco.track.eco_tracking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.eco.track.eco_tracking_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}