package com.example.schoolmanagement.services;

import com.example.schoolmanagement.entities.CustomUserDetails;
import com.example.schoolmanagement.entities.User;
import com.example.schoolmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.getUserByUsername(username)
//                .orElseThrow(
//                        () -> new UsernameNotFoundException("Username " + username + " not found")
//                );
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        return user.map(CustomUserDetails::new).get();
    }

    public UserDetails getUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByUsername(username);
    }
}
