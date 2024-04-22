package com.projet.v1.security.administration;

import com.projet.v1.user.Role;

public record AdministrationUserDto(Integer userId, Role role, boolean accountNonLocked, String pseudo) {
}
