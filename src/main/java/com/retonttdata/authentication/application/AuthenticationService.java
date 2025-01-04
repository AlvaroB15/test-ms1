package com.retonttdata.authentication.application;

import com.retonttdata.authentication.domain.model.Authentication;
import com.retonttdata.authentication.domain.model.Credentials;
import com.retonttdata.authentication.domain.port.input.AuthenticationUseCase;
import com.retonttdata.authentication.domain.port.output.UserAuthenticationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService implements AuthenticationUseCase {

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthenticationService(UserAuthenticationRepository userAuthenticationRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtProvider jwtProvider) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Credentials credentials) {
        return userAuthenticationRepository.findByEmail(credentials.getEmail())
                .filter(user -> passwordEncoder.matches(credentials.getPassword(), user.getPassword()))
                .map(user -> Authentication.builder()
                        .email(user.getEmail())
                        .token(jwtProvider.generateToken(user.getEmail()))
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }
}