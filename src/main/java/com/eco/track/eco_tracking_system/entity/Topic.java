package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Topic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id", nullable = false)
    private Long topicID;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(
        mappedBy="topic",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<UserProfile> userProfiles;

    @OneToMany(
        mappedBy="topic",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<CommunityReport> communityReports;
}
