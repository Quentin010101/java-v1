package com.projet.v1.security.administration;

import com.projet.v1.security.administration.userConfiguration.dto.UserConfigurationDto;
import com.projet.v1.user.Role;

import java.util.Date;

public record AdministrationUserDto(Integer userId, Role role, boolean accountNonLocked, String pseudo, Date dateCreation, Date dateLastConnection, UserConfigurationDto config) {
}
