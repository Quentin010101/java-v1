package com.projet.v1.security.userConfiguration;

import com.projet.v1.security.userConfiguration.dto.ModuleDto;
import com.projet.v1.security.userConfiguration.dto.UserConfigurationDto;
import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserConfigurationService {

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;
    @Autowired
    private UserService userService;

    public UserConfigurationDto readActifUserConf(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapperToDto(userConfigurationRepository.findByUserId(user.getUserId()).orElseThrow());
    }

    public UserConfigurationDto update(UserConfigurationDao userConfiguration){
        return mapperToDto(userConfigurationRepository.save(userConfiguration));
    }

    public void save(UserConfigurationDao config) {
        userConfigurationRepository.save(config);
    }

    private UserConfigurationDto mapperToDto(UserConfigurationDao userConfigurationDao){
        return new UserConfigurationDto(
                userConfigurationDao.getUserConfigurationId(),
                userConfigurationDao.getUserId(),
                this.transformToDto(userConfigurationDao.getModules())
        );
    }

    private List<ModuleDto> transformToDto(List<ModuleEnum> listEnum){
        List<ModuleDto> newList = new ArrayList<>();
        for(ModuleEnum e : listEnum){
            newList.add(new ModuleDto(e.getModuleId(),e.getName()));
        }
        return newList;
    }
}
