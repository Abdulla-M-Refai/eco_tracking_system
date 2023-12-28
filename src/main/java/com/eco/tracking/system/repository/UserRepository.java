package com.eco.tracking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.eco.tracking.system.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}