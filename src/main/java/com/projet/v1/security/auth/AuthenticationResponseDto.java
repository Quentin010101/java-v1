package com.projet.v1.security.auth;

import java.util.Date;

public record AuthenticationResponseDto(Integer userId,String pseudo, String token, Long expDateToken) {
}
