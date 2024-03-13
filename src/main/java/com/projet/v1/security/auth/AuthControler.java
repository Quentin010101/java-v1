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
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/authentication")
public class AuthControler {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody AuthenticationDto authenticationDto){
        log.info("authentication with pseudo: " + authenticationDto.pseudo() + " and password: " + authenticationDto.password());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.pseudo(), authenticationDto.password())
        );
        log.info("authentication success: " + authentication.isAuthenticated());

        if(authentication.isAuthenticated()){
            String token;
            if(authenticationDto.stayLogged()){
                token = this.jwtService.generateLong(authenticationDto.pseudo());
            }else{
                token = this.jwtService.generate(authenticationDto.pseudo());
            }

            log.info(jwtService.getExpirationDateFromToken(token).toString());
            return new AuthenticationResponseDto(
                    authenticationDto.pseudo(),
                    token,
                    this.jwtService.getExpirationDateFromToken(token).getTime()
            );
        }
        return null;
    }

}
