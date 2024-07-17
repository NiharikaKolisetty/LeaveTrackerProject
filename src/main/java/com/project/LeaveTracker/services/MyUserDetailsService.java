package com.project.LeaveTracker.services;

import com.project.LeaveTracker.entity.MyUser;
import com.project.LeaveTracker.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(addRolePrefix(userObj.getRole())) // Prefix roles with "ROLE_"
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] addRolePrefix(String roles) {
        if (roles == null || roles.isEmpty()) {
            return new String[]{"USER"}; // Default role if none specified
        }
        return Arrays.stream(roles.split(","))
                .map(r -> r.trim()) // Remove any leading/trailing whitespace
                .toArray(String[]::new);
    }
}
