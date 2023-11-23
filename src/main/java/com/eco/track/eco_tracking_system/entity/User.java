package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<UserProfile> userProfiles;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<EnvironmentalData> environmentalDataList;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<CommunityReport> communityReports;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<SustainabilityScore> sustainabilityScores;

    public void addUserProfile(UserProfile userProfile)
    {
        if(userProfiles == null)
            userProfiles = new ArrayList<>();

        userProfiles.add(userProfile);
    }

    public void addEnvironmentalData(EnvironmentalData environmentalData)
    {
        if(environmentalDataList == null)
            environmentalDataList = new ArrayList<>();

        environmentalDataList.add(environmentalData);
    }

    public void addCommunityReport(CommunityReport communityReport)
    {
        if(communityReports == null)
            communityReports = new ArrayList<>();

        communityReports.add(communityReport);
    }

    public void addSustainabilityScore(SustainabilityScore sustainabilityScore)
    {
        if(sustainabilityScores == null)
            sustainabilityScores = new ArrayList<>();

        sustainabilityScores.add(sustainabilityScore);
    }
}