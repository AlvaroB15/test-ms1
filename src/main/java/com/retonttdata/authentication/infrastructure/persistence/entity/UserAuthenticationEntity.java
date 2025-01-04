package com.retonttdata.authentication.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class UserAuthenticationEntity {
    @Id
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
}
