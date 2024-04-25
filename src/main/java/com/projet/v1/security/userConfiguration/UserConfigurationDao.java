package com.projet.v1.security.userConfiguration;

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
    @Column(unique=true)
    private Integer userId;
    private List<ModuleEnum> modules;
}
