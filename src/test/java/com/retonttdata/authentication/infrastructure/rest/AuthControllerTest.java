package com.retonttdata.authentication.infrastructure.rest;

import com.retonttdata.authentication.application.dto.AuthenticationRequest;
import com.retonttdata.authentication.application.dto.AuthenticationResponse;
import com.retonttdata.authentication.domain.model.Authentication;
import com.retonttdata.authentication.domain.model.Credentials;
import com.retonttdata.authentication.domain.port.input.AuthenticationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationUseCase authenticationUseCase;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_ValidRequest_ReturnsToken() {
        // Arrange
        String email = "test@example.com";
        String password = "Password123";
        String token = "jwt.token.example";

        AuthenticationRequest request = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();

        Authentication authentication = Authentication.builder()
                .email(email)
                .token(token)
                .build();

        when(authenticationUseCase.authenticate(any(Credentials.class)))
                .thenReturn(Mono.just(authentication));

        // Act & Assert
        StepVerifier.create(authController.login(request))
                .expectNext(AuthenticationResponse.builder()
                        .token(token)
                        .build())
                .verifyComplete();
    }

    @Test
    void login_InvalidCredentials_ReturnsError() {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("wrongPassword")
                .build();

        when(authenticationUseCase.authenticate(any(Credentials.class)))
                .thenReturn(Mono.error(new RuntimeException("Invalid credentials")));

        // Act & Assert
        StepVerifier.create(authController.login(request))
                .expectError(RuntimeException.class)
                .verify();
    }
}