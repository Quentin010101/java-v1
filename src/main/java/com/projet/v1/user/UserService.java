package com.projet.v1.user;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.security.SecurityConfiguration;
import com.projet.v1.security.administration.AdministrationNewUserDto;
import com.projet.v1.security.administration.AdministrationUserDto;
import com.projet.v1.security.administration.userConfiguration.ModuleEnum;
import com.projet.v1.security.administration.userConfiguration.UserConfigurationDao;
import com.projet.v1.security.administration.userConfiguration.dto.ModuleDto;
import com.projet.v1.security.administration.userConfiguration.dto.UserConfigurationDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j

@Service
public class UserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.trace("Trying to get the user : " + username);
        return userRepository.findByPseudo(username).orElseThrow(()-> new UsernameNotFoundException("No user found with this credential " + username));
    }

    public AdministrationUserDto create(AdministrationNewUserDto user) throws IncorrectRequestInformation {
        if(userRepository.existsByPseudo(user.pseudo())) throw new IncorrectRequestInformation("User pseudo already exist");
        User newUser = new User();
        newUser.setAccountNonLocked(false);
        newUser.setDateCreation(new Date());
        newUser.setRole(Role.USER);
        newUser.setPassword(user.password());
        newUser.setPseudo(user.pseudo());
        UserConfigurationDao conf = new UserConfigurationDao();
        conf.setModules(new ArrayList<>());
        newUser.setConfig(conf);
        User saved = userRepository.save(newUser);
        return mapperUserTODto(saved);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<AdministrationUserDto> getAllUsers(Role role){
        List<User> list = userRepository.findByRole(role);
        List<AdministrationUserDto> dtoList = new ArrayList<>();
        for(User user: list){
            AdministrationUserDto admUser = mapperUserTODto(user);
            dtoList.add(admUser);
        }
        return dtoList;
    }

    public AdministrationUserDto administrationUpdateUser(AdministrationUserDto userDto) throws IncorrectRequestInformation {
        if(userDto.userId() == null) throw new IncorrectRequestInformation("User id is not set.");
        User user = userRepository.findById(userDto.userId()).orElseThrow();
        user.setAccountNonLocked(userDto.accountNonLocked());
        UserConfigurationDao configDao = user.getConfig();
        configDao.setModules(mapperModuleToDao(userDto.config().getModules()));
        user.setConfig(configDao);
        User newUser = userRepository.save(user);
        return mapperUserTODto(newUser);
    }

    public AdministrationUserDto returnActifUser(){
       User actifUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return mapperUserTODto(actifUser);
    }

    private AdministrationUserDto mapperUserTODto(User user){
        return new AdministrationUserDto(
                user.getUserId(),
                user.getRole(),
                user.isAccountNonLocked(),
                user.getPseudo(),
                user.getDateCreation(),
                user.getDateLastConnection(),
                mapperConfigToDto(user.getConfig())
        );
    }

    private UserConfigurationDto mapperConfigToDto(UserConfigurationDao config){
        return new UserConfigurationDto(
                config.getUserConfigurationId(),
                mapperModuleToDto(config.getModules())
        );
    }

    public List<ModuleDto> mapperModuleToDto(List<ModuleEnum> modules){
        List<ModuleDto> list = new ArrayList<>();
        for(ModuleEnum module :modules){
            ModuleDto m = new ModuleDto(module.getModuleId(),module.getName());
            list.add(m);
        }
        return list;
    }

    public List<ModuleEnum> mapperModuleToDao(List<ModuleDto> modules){
        List<ModuleEnum> list = new ArrayList<>();
        for(ModuleDto module :modules){
            log.info(module.getName());
            ModuleEnum m = ModuleEnum.valueOf(module.getModuleId());
            list.add(m);
        }
        return list;
    }

}
