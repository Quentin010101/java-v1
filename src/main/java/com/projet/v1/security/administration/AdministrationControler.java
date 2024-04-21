package com.projet.v1.security.administration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.user.Role;
import com.projet.v1.user.User;
import com.projet.v1.user.UserRepository;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/administration")
public class AdministrationControler {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("users/all")
    public ResponseObjectDto<List<User>> getUser(){
        List<User> usersList = userService.getAllUsers(Role.USER);
        for(User u : usersList){
            log.info(u.toString2());
        }
        return new ResponseObjectDto<>(new ResponseDto("List of all users.", true), usersList);
    }
}
