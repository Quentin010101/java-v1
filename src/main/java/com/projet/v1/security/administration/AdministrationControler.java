package com.projet.v1.security.administration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.user.Role;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/administration")
public class AdministrationControler {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("user/create")
    public ResponseObjectDto<AdministrationUserDto> create(@RequestBody AdministrationNewUserDto administrationNewUserDto) throws IncorrectRequestInformation {
        AdministrationNewUserDto userEncoded = new AdministrationNewUserDto(administrationNewUserDto.pseudo(), passwordEncoder.encode(administrationNewUserDto.password()));
        AdministrationUserDto userResponse = userService.create(userEncoded);
        return new ResponseObjectDto<>(new ResponseDto("new User", true), userResponse);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("user/delete/{id}")
    public ResponseDto delete(@PathVariable("id") Integer id) throws IncorrectRequestInformation {
        userService.deleteUser(id);
        return new ResponseDto("User deleted", true);
    }

    @ExceptionHandler(IncorrectRequestInformation.class)
    public ResponseEntity<Object> handle(IncorrectRequestInformation ex){
        return new ResponseEntity<>(ex, HttpStatusCode.valueOf(650));
    }
}
