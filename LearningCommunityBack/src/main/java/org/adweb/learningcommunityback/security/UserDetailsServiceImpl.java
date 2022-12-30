package org.adweb.learningcommunityback.security;

import org.adweb.learningcommunityback.entity.db.User;
import org.adweb.learningcommunityback.repository.UserRepository;
import org.adweb.learningcommunityback.security.entity.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        JwtUser jwtUser = new JwtUser();
        if (user != null) {
            jwtUser.setUsername(user.getUsername());
            jwtUser.setPassword(user.getPassword());
            jwtUser.setRole(user.getRole());
        }
        return jwtUser;
    }
}
