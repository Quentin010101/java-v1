package com.projet.v1.security.userConfiguration;

import com.projet.v1.user.User;
import com.projet.v1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationService {

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;
    @Autowired
    private UserService userService;

    public UserConfigurationDao readActifUserConf(){
        String pseudo = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User  actifUser = (User)userService.loadUserByUsername(pseudo);
        return userConfigurationRepository.findByUserId(actifUser.getUserId()).orElseThrow();
    }

    public UserConfigurationDao update(UserConfigurationDao userConfiguration){
        return userConfigurationRepository.save(userConfiguration);
    }
}
