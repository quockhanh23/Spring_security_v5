package com.example.testsecurityv5.service.impl;

import com.example.testsecurityv5.models.User;
import com.example.testsecurityv5.models.UserPrinciple;
import com.example.testsecurityv5.repositories.UserRepository;
import com.example.testsecurityv5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        UserPrinciple userPrinciple = new UserPrinciple();
        userOptional.ifPresent(user -> {
            userPrinciple.setId(userOptional.get().getId());
            userPrinciple.setUsername(userOptional.get().getUsername());
            userPrinciple.setPassword(userOptional.get().getPassword());
            List<GrantedAuthority> authorities = userOptional.get().getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
            userPrinciple.setRoles(authorities);
        });
        return userPrinciple;
    }
}
