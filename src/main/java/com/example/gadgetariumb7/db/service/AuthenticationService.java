package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.config.jwt.JwtService;
import com.example.gadgetariumb7.db.entity.Role;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.repository.RoleRepository;
import com.example.gadgetariumb7.db.repository.UserRepository;
import com.example.gadgetariumb7.dto.request.AuthenticationRequest;
import com.example.gadgetariumb7.dto.request.RegisterRequest;
import com.example.gadgetariumb7.dto.response.AuthenticationResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    private final Role role;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("gadgetarium.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.getById(2L))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .roleName(user.getRole().getRoleName())
                .email(user.getEmail())
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
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .roleName(user.getRole().getRoleName())
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        User user;
        if (!repository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User();
            String[] name = firebaseToken.getName().split(" ");
            newUser.setFirstName(name[0]);
            newUser.setLastName(name[1]);
            newUser.setEmail(firebaseToken.getEmail());
            newUser.setPassword(firebaseToken.getEmail());
            if (role.getRoleName().equals("User")) {
                newUser.setRole(role);
            }
            user=repository.save(newUser);
        }
        user=repository.findByEmail(firebaseToken.getEmail()).orElseThrow(()->{
            log.error("User with email:{} not found ", firebaseToken.getEmail());
            throw new NotFoundException(String.format("Пользователь с таким электронным адрессом %s не найден!",firebaseToken.getEmail()));
        });
        String token =jwtService.generateToken(user);
        log.info("User with email: {} successfully authenticate with google ",firebaseToken.getEmail());
        return new AuthenticationResponse(token , role.getRoleName(), user.getEmail());
    }


}