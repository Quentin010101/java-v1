package com.projet.v1.security;

import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Access Filter token.");

        String token = null;
        String pseudo = null;

        final String authorization = request.getHeader("Authorization");
        log.info("Authorization = " + authorization);
        if(authorization != null && authorization.startsWith("Bearer ")){
            if(authorization.length() > 7){
                token = authorization.substring(7);
            }

            if(token != null){
                try{
                    if(!jwtService.isTokenExpired(token)){
                        pseudo = jwtService.extractPseudo(token);
                    }
                }catch (MalformedJwtException e){
                    log.info(e.getMessage());
                }
            }
        }
        log.info("Pseudo = " + pseudo);
        if(pseudo != null && SecurityContextHolder.getContext().getAuthentication() == null){
            log.info("Start building auth context");
            User user = (User) userService.loadUserByUsername(pseudo);
            log.info("User retrieve : " + user);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
