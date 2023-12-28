package com.eco.tracking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.eco.tracking.system.entity.ProfileFollowers;
import org.springframework.data.jpa.repository.Query;

public interface ProfileFollowersRepository extends JpaRepository<ProfileFollowers, Long>
{
    @Query(
        value =
            "SELECT pf FROM ProfileFollowers pf " +
            "WHERE pf.user.userID = :userID AND pf.userProfile.profileID = :profileID"
    )
    Optional<ProfileFollowers> findByUserIDAndProfileID(long userID, long profileID);
}
