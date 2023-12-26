package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long profileID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable = false)
    private Topic topic;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "userProfile",
        cascade = CascadeType.ALL
    )
    private List<EnvironmentalData> environmentalDataList;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "userProfile",
        cascade = CascadeType.ALL
    )
    private List<ProfileFollowers> follower;
}
