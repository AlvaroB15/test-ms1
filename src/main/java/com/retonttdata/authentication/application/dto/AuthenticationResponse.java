package com.retonttdata.authentication.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {
    String token;
}