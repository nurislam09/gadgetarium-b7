package com.example.gadgetariumb7.service;

import com.example.gadgetariumb7.config.JwtService;
import com.example.gadgetariumb7.db.entity.Role;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.dto.request.AuthenticationRequest;
import com.example.gadgetariumb7.dto.request.RegisterRequest;
import com.example.gadgetariumb7.dto.response.AuthenticationResponse;
import com.example.gadgetariumb7.repository.RoleRepository;
import com.example.gadgetariumb7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.getById(2L))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @PostConstruct
    public void initMethod() {

        Role role1 = new Role();
        role1.setRoleName("Admin");

        Role role2 = new Role();
        role2.setRoleName("User");

        var user = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .role(role1)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        repository.save(user);
        user.setRole(role1);
        role1.setUsers(Arrays.asList(user));
        roleRepository.save(role1);
        roleRepository.save(role2);
    }
}