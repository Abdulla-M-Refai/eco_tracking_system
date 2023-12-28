package com.eco.tracking.system.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "educational_resources")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;
}
