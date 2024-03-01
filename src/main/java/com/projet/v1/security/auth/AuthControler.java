package com.projet.v1.security.auth;

import com.projet.v1.security.JwtService;
import com.projet.v1.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("authentication")
public class AuthControler {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @PostMapping("login")
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

    @GetMapping("refreshToken")
    public AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response){
        log.info("access refresh token");

        String refreshtoken = null;
        String pseudo = null;

        final String authorization = request.getHeader("Authorization");
        log.info("Authorization = " + authorization);
        if(authorization == null || !authorization.startsWith("Bearer ")){
            return null;
        }

        refreshtoken = authorization.substring(7);
        log.info("Token expiration = " + jwtService.isTokenExpired(refreshtoken));
        pseudo = jwtService.extractPseudo(refreshtoken);
        if(pseudo !=null && !jwtService.isTokenExpired(refreshtoken)){
            String refreshTokenBase = this.userService.getRefreshToken(pseudo);
            if(refreshTokenBase != null && refreshTokenBase.equals(refreshtoken)){
                return new AuthenticationResponseDto(refreshtoken, this.jwtService.generate(pseudo));
            }
        }

        return null;
    }
}
