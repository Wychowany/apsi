package com.apsi.modules.user.domain;

import com.apsi.generic.AbstractIdEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "application_users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractIdEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemRole systemRole;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean active;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean accepted;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean confirmed;

    @Transient
    private Set<SimpleGrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return confirmed;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accepted;
    }
}
