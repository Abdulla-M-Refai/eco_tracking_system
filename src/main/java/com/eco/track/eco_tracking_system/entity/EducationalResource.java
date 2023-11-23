package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "EducationalResource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalResource
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
}
