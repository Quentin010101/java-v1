package com.projet.v1.security;

import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class UserDateFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("date filter out");
        if(authentication != null){
            log.info("date filter in");
            String pseudo = authentication.getName();
            User user = (User) userService.loadUserByUsername(pseudo);
            user.setDateLastConnection(new Date());
            userService.save(user);
        }


        filterChain.doFilter(request, response);
    }
}
