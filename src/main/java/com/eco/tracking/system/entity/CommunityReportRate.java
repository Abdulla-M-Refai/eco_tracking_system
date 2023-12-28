package com.eco.tracking.system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "community_report_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityReportRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private CommunityReport communityReport;

    @Column(name = "rate", nullable = false)
    private Float rate;
}
