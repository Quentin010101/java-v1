package com.projet.v1.security.userConfiguration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
import com.projet.v1.security.userConfiguration.dto.UserConfigurationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("${frontend.server.url}")
@RequestMapping("/configuration")
@Slf4j
public class UserConfigurationController {

    @Autowired
    private UserConfigurationService userConfigurationService;

    @GetMapping("/read")
    public ResponseObjectDto<UserConfigurationDto> readConf(){
        log.info("get config");
        UserConfigurationDto conf = userConfigurationService.readActifUserConf();
        return new ResponseObjectDto<>(new ResponseDto("User conf", true), conf);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/update")
    public ResponseObjectDto<UserConfigurationDto> update(@RequestBody UserConfigurationDao config){
        UserConfigurationDto conf = userConfigurationService.update(config);
        return new ResponseObjectDto<>(new ResponseDto("User conf", true), conf);
    }


}
