package com.retonttdata.authentication.infrastructure.rest;

import com.retonttdata.authentication.application.dto.AuthenticationRequest;
import com.retonttdata.authentication.application.dto.AuthenticationResponse;
import com.retonttdata.authentication.domain.model.Credentials;
import com.retonttdata.authentication.domain.port.input.AuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationUseCase authenticationUseCase;

    @PostMapping("/login")
    public Mono<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        Credentials credentials = new Credentials(request.getEmail(), request.getPassword());
        return authenticationUseCase.authenticate(credentials)
                .map(auth -> AuthenticationResponse.builder()
                        .token(auth.getToken())
                        .build());
    }
}