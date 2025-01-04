package com.retonttdata.authentication.domain.port.output;

import com.retonttdata.authentication.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserAuthenticationRepository {
    Mono<User> findByEmail(String email);
}