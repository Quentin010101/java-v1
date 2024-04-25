package com.projet.v1.security.auth;

import java.util.Date;

public record AuthenticationResponseDto(String pseudo, String token, Long expDateToken) {
}
