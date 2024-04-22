package com.projet.v1.user;

import com.projet.v1.exception.IncorrectRequestInformation;
import com.projet.v1.security.SecurityConfiguration;
import com.projet.v1.security.administration.AdministrationUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void save(User user){
        userRepository.save(user);
    }

    public List<AdministrationUserDto> getAllUsers(Role role){
        List<User> list = userRepository.findByRole(role);
        List<AdministrationUserDto> dtoList = new ArrayList<>();
        for(User user: list){
            AdministrationUserDto admUser = new AdministrationUserDto(user.getUserId(),user.getRole(),user.isAccountNonLocked(),user.getPseudo());
            dtoList.add(admUser);
        }
        return dtoList;
    }

    public AdministrationUserDto administrationUpdateUser(AdministrationUserDto userDto) throws IncorrectRequestInformation {
        if(userDto.userId() == null) throw new IncorrectRequestInformation("User id is not set.");
        User user = userRepository.findById(userDto.userId()).orElseThrow();
        user.setAccountNonLocked(userDto.accountNonLocked());
        User newUser = userRepository.save(user);
        return new AdministrationUserDto(newUser.getUserId(),newUser.getRole(),newUser.isAccountNonLocked(),newUser.getPseudo());
    }

}
