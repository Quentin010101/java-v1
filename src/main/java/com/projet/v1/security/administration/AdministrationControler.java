package com.projet.v1.security.administration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.security.administration.userConfiguration.dto.ModuleDto;
import com.projet.v1.user.Role;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user/all")
    public ResponseObjectDto<List<AdministrationUserDto>> getUsers(){
        List<AdministrationUserDto> usersList = userService.getAllUsers(Role.USER);
        List<AdministrationUserDto> usersList2 = userService.getAllUsers(Role.DEMO);
        usersList.addAll(usersList2);
        return new ResponseObjectDto<>(new ResponseDto("List of all users.", true), usersList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("user/update")
    public ResponseObjectDto<AdministrationUserDto> updateUser(@RequestBody AdministrationUserDto administrationUserDto) throws IncorrectRequestInformation {
        log.info("update");
        log.info(administrationUserDto.toString());
        for(ModuleDto m : administrationUserDto.config().getModules()){
            log.info(m.toString());
        }
        AdministrationUserDto userResponse = userService.administrationUpdateUser(administrationUserDto);
        return new ResponseObjectDto<>(new ResponseDto("List of all users.", true), userResponse);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("user/create")
    public ResponseObjectDto<AdministrationUserDto> createUser(@RequestBody AdministrationNewUserDto administrationNewUserDto) throws IncorrectRequestInformation {
        AdministrationNewUserDto userEncoded = new AdministrationNewUserDto(administrationNewUserDto.pseudo(), passwordEncoder.encode(administrationNewUserDto.password()));
        AdministrationUserDto userResponse = userService.create(userEncoded);
        return new ResponseObjectDto<>(new ResponseDto("new User", true), userResponse);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("user/delete/{id}")
    public ResponseDto deleteUser(@PathVariable("id") Integer id) throws IncorrectRequestInformation {
        userService.deleteUser(id);
        return new ResponseDto("User deleted", true);
    }

    @ExceptionHandler(IncorrectRequestInformation.class)
    public ResponseEntity<Object> handle(IncorrectRequestInformation ex){
        return new ResponseEntity<>(ex, HttpStatusCode.valueOf(650));
    }
}
