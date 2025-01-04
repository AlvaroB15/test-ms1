package com.retonttdata.authentication.infrastructure.persistence;

import com.retonttdata.authentication.infrastructure.persistence.entity.UserAuthenticationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ReactiveUserAuthenticationRepository extends R2dbcRepository<UserAuthenticationEntity, Long> {
    Mono<UserAuthenticationEntity> findByEmail(String email);
}
