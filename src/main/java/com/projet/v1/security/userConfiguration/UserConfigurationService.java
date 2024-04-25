package com.projet.v1.security.userConfiguration;

import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserConfigurationService {

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;
    @Autowired
    private UserService userService;

    public UserConfigurationDao readActifUserConf(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userConfigurationRepository.findByUserId(user.getUserId()).orElseThrow();
    }

    public UserConfigurationDao update(UserConfigurationDao userConfiguration){
        return userConfigurationRepository.save(userConfiguration);
    }

    public void save(UserConfigurationDao config) {
        userConfigurationRepository.save(config);
    }
}
