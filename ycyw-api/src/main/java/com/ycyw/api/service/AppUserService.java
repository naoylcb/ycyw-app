package com.ycyw.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ycyw.api.dto.AuthRequestDTO;
import com.ycyw.api.dto.AuthResponseDTO;
import com.ycyw.api.model.AppUser;
import com.ycyw.api.repository.UserRepository;
import com.ycyw.api.service.JWTService;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public AuthResponseDTO connectUser(AuthRequestDTO authRequest) {
        AppUser user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setUserId(user.getId());

        return response;
    }
}