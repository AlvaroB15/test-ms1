package com.retonttdata.authentication.application;

import com.retonttdata.authentication.domain.model.Authentication;
import com.retonttdata.authentication.domain.model.Credentials;
import com.retonttdata.authentication.domain.model.User;
import com.retonttdata.authentication.domain.port.output.UserAuthenticationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
                userAuthenticationRepository,
                passwordEncoder,
                jwtProvider
        );
    }

    @Test
    void authenticate_ValidCredentials_ReturnsAuthentication() {
        // Arrange
        String email = "test@example.com";
        String password = "Password123";
        String encodedPassword = "encodedPassword";
        String token = "jwt.token.example";

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        Credentials credentials = new Credentials(email, password);

        when(userAuthenticationRepository.findByEmail(email))
                .thenReturn(Mono.just(user));
        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);
        when(jwtProvider.generateToken(email))
                .thenReturn(token);

        // Act & Assert
        StepVerifier.create(authenticationService.authenticate(credentials))
                .expectNext(Authentication.builder()
                        .email(email)
                        .token(token)
                        .build())
                .verifyComplete();
    }

    @Test
    void authenticate_InvalidCredentials_ReturnsError() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";
        String encodedPassword = "encodedPassword";

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        Credentials credentials = new Credentials(email, password);

        when(userAuthenticationRepository.findByEmail(email))
                .thenReturn(Mono.just(user));
        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(false);

        // Act & Assert
        StepVerifier.create(authenticationService.authenticate(credentials))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void authenticate_UserNotFound_ReturnsError() {
        // Arrange
        Credentials credentials = new Credentials("nonexistent@example.com", "password");

        when(userAuthenticationRepository.findByEmail(anyString()))
                .thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(authenticationService.authenticate(credentials))
                .expectError(RuntimeException.class)
                .verify();
    }
}