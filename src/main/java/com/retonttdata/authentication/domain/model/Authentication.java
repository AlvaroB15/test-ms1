package com.retonttdata.authentication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Value
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Authentication {
    String email;
    String token;
}