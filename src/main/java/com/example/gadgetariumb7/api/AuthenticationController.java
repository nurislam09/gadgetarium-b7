package com.example.gadgetariumb7.api;

import com.example.gadgetariumb7.dto.request.AuthenticationRequest;
import com.example.gadgetariumb7.dto.request.RegisterRequest;
import com.example.gadgetariumb7.dto.response.AuthenticationResponse;
import com.example.gadgetariumb7.db.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }

}
