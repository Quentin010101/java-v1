package com.projet.v1.security.administration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.user.Role;
import com.projet.v1.user.User;
import com.projet.v1.user.UserRepository;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/administration")
public class AdministrationControler {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("user/all")
    public ResponseObjectDto<List<AdministrationUserDto>> getUser(){
        List<AdministrationUserDto> usersList = userService.getAllUsers(Role.USER);
        List<AdministrationUserDto> usersList2 = userService.getAllUsers(Role.DEMO);
        usersList.addAll(usersList2);
        return new ResponseObjectDto<>(new ResponseDto("List of all users.", true), usersList);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("user/update")
    public ResponseObjectDto<AdministrationUserDto> update(@RequestBody AdministrationUserDto administrationUserDto) throws IncorrectRequestInformation {
        AdministrationUserDto userResponse = userService.administrationUpdateUser(administrationUserDto);
        return new ResponseObjectDto<>(new ResponseDto("List of all users.", true), userResponse);
    }
}
