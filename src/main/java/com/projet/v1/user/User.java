package com.projet.v1.user;

import com.projet.v1.security.administration.userConfiguration.UserConfigurationDao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name="_user")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    @Column(unique=true)
    private String pseudo;
    private String password;
    private Role role;
    private boolean isAccountNonLocked;
    private Date dateCreation;
    private Date dateLastConnection;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userConfigurationId", referencedColumnName = "userConfigurationId")
    private UserConfigurationDao config;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public String toString2() {
        return "User{" +
                "userId=" + userId +
                ", pseudo='" + pseudo + '\'' +
                ", role=" + role +
                ", isAccountNonLocked=" + isAccountNonLocked +
                '}';
    }
}
