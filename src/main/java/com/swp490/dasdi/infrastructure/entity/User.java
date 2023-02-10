package com.swp490.dasdi.infrastructure.entity;

import com.swp490.dasdi.infrastructure.entity.enumeration.AuthProvider;
import com.swp490.dasdi.infrastructure.entity.enumeration.PreferredFoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "forte_position")
    private String fortePosition;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "preferred_foot")
    private PreferredFoot preferredFoot;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "players")
    private List<Team> team;
}
