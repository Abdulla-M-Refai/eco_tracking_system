package com.eco.tracking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.tracking.system.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>
{

}