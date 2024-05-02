package com.projet.v1.security.administration.userConfiguration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.security.administration.AdministrationUserDto;
import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/administration/configuration")
public class UserConfigurationController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseObjectDto<AdministrationUserDto> getConfig(){
        return new ResponseObjectDto<>(new ResponseDto("List of modules", true), userService.returnActifUser());
    }

    @GetMapping("modules")
    public ResponseObjectDto<List<ModuleEnum>> getModules(){
        List<ModuleEnum> list = List.of(ModuleEnum.values());
        return new ResponseObjectDto<>(new ResponseDto("List of modules", true), list);
    }


}
