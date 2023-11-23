package com.eco.track.eco_tracking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.track.eco_tracking_system.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>
{

}