package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "community_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "location")
    private String location;
}