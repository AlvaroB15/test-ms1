package com.retonttdata.authentication.infrastructure.adapter;

import com.retonttdata.authentication.domain.model.User;
import com.retonttdata.authentication.domain.port.output.UserAuthenticationRepository;
import com.retonttdata.authentication.infrastructure.persistence.ReactiveUserAuthenticationRepository;
import com.retonttdata.authentication.infrastructure.persistence.entity.UserAuthenticationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserAuthenticationRepository {
    private final ReactiveUserAuthenticationRepository reactiveUserAuthenticationRepository;

    @Override
    public Mono<User> findByEmail(String email) {
        return reactiveUserAuthenticationRepository.findByEmail(email)
                .map(this::toDomain);
    }

    private User toDomain(UserAuthenticationEntity entity) {
        return User.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .phone(entity.getPhone())
                .build();
    }
}