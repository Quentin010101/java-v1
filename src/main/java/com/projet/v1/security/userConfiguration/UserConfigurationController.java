package com.projet.v1.security.userConfiguration;

import com.projet.v1.dto.ResponseDto;
import com.projet.v1.dto.ResponseObjectDto;
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
    public ResponseObjectDto<UserConfigurationDao> readConf(){
        log.info("get config");
        UserConfigurationDao conf = userConfigurationService.readActifUserConf();
        return new ResponseObjectDto<>(new ResponseDto("User conf", true), conf);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/update")
    public ResponseObjectDto<UserConfigurationDao> update(@RequestBody UserConfigurationDao config){
        UserConfigurationDao conf = userConfigurationService.update(config);
        return new ResponseObjectDto<>(new ResponseDto("User conf", true), conf);
    }


}
