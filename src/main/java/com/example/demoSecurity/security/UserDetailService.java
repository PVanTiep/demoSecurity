package com.example.demoSecurity.security;

import com.example.demoSecurity.entity.User;
import com.example.demoSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    public UserDetails loadUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() == false){
            throw new UsernameNotFoundException("Long "+ userId);
        }
        return new CustomUserDetails(user.get());
    }
}