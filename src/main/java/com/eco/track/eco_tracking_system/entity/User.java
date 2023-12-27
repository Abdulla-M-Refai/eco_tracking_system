package com.eco.track.eco_tracking_system.entity;

import lombok.*;
import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Collection;

import com.eco.track.eco_tracking_system.entity.Enum.UserType;

@Entity
@Table(name = "User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails
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

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<UserProfile> userProfiles;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user",
        cascade = CascadeType.ALL
    )
    private List<CommunityReport> communityReports;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<SustainabilityScore> sustainabilityScores;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    private List<ProfileFollowers> following;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }
}