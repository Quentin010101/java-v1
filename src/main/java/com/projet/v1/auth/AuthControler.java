package com.projet.v1.auth;

import com.projet.v1.dto.AuthenticationDto;
import com.projet.v1.dto.AuthenticationResponseDto;
import com.projet.v1.dto.RefreshTokenDto;
import com.projet.v1.security.JwtService;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(name = "/authentication")
public class AuthControler {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @PostMapping(path = "login")
    public AuthenticationResponseDto login(@RequestBody AuthenticationDto authenticationDto){
        log.info("authentication with pseudo: " + authenticationDto.pseudo() + " and password: " + authenticationDto.password());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.pseudo(), authenticationDto.password())
        );
        log.info("authentication success: " + authentication.isAuthenticated());

        if(authentication.isAuthenticated()){
            String token = this.jwtService.generate(authenticationDto.pseudo());
            String refreshToken = this.jwtService.generateRefreshJwt(authenticationDto.pseudo());
            userService.setRefreshtoken(authenticationDto.pseudo(), refreshToken);
            return new AuthenticationResponseDto(refreshToken, token);
        }
        return null;
    }

    @PostMapping(path = "refreshToken")
    public AuthenticationResponseDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
        log.info("access refresh token");

        if(refreshTokenDto.refreshToken() != null && this.jwtService.isTokenExpired(refreshTokenDto.refreshToken())){
            String pseudo = this.jwtService.extractPseudo(refreshTokenDto.refreshToken());
            if(pseudo != null){
                String refreshTokenBase = this.userService.getRefreshToken(pseudo);
                if(refreshTokenBase != null && refreshTokenBase.equals(refreshTokenDto.refreshToken())){
                    return new AuthenticationResponseDto(refreshTokenDto.refreshToken(), this.jwtService.generate(pseudo));
                }
            }
        }

        return null;
    }
}
