package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "report", nullable = false, columnDefinition = "TEXT")
    private String report;

    @Column(name = "rate", nullable = false)
    private Float rate;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "communityReport",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    private List<CommunityReportRate> communityReportRates;
}