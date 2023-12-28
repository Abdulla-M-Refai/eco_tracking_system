package com.eco.tracking.system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "environmental_data_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalDataRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environmental_data_id")
    private EnvironmentalData environmentalData;

    @Column(name = "rate", nullable = false)
    private Float rate;
}
