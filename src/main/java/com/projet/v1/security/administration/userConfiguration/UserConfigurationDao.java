package com.projet.v1.security.administration.userConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projet.v1.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="user_configuration")
@Getter
@Setter
public class UserConfigurationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userConfigurationId;
    private List<ModuleEnum> modules;

    @OneToOne(mappedBy = "config")
    @JsonIgnore
    private User user;
}
