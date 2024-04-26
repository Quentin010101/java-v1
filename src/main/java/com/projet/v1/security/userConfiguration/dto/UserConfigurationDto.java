package com.projet.v1.security.userConfiguration.dto;


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
    private Integer userId;
    private List<ModuleDto> modules;
}
