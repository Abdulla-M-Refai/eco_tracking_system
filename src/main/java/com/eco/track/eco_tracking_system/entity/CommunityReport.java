package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "EnvironmentalData")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable = false)
    private Topic topic;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "location", length = 255)
    private String location;
}