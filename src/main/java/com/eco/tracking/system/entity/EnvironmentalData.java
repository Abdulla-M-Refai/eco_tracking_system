package com.eco.tracking.system.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDateTime;

import com.eco.tracking.system.entity.Enum.DataSource;

@Entity
@Table(name = "environmental_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long dataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_source", nullable = false)
    private DataSource dataSource;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "rate", nullable = false)
    private Float rate;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "environmentalData",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    private List<EnvironmentalDataRate> environmentalDataRates;
}