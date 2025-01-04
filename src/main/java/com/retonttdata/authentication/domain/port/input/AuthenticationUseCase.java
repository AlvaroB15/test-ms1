package com.retonttdata.authentication.domain.port.input;

import com.retonttdata.authentication.domain.model.Authentication;
import com.retonttdata.authentication.domain.model.Credentials;
import reactor.core.publisher.Mono;

public interface AuthenticationUseCase {
    Mono<Authentication> authenticate(Credentials credentials);
}