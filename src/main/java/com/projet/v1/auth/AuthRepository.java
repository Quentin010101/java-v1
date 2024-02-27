package com.projet.v1.auth;

import com.projet.v1.dto.AuthenticationDto;
import com.projet.v1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(name = "/authentication")
public class AuthRepository {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "login")
    public Map<String, String> login(@RequestBody AuthenticationDto authenticationDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.pseudo(), authenticationDto.password())
        );
    }
}
