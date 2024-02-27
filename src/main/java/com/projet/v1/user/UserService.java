package com.projet.v1.user;

import com.projet.v1.security.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
