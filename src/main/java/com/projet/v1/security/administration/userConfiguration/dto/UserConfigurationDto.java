package com.projet.v1.security.administration.userConfiguration.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConfigurationDto {

    private Integer userConfigurationId;
    private List<ModuleDto> modules;

    @Override
    public String toString() {
        return "UserConfigurationDto{" +
                "userConfigurationId=" + userConfigurationId +
                ", modules=" + modules +
                '}';
    }
}
