package com.retonttdata.authentication.infrastructure.config;

import com.retonttdata.authentication.application.AuthenticationService;
import com.retonttdata.authentication.application.JwtProvider;
import com.retonttdata.authentication.domain.port.input.AuthenticationUseCase;
import com.retonttdata.authentication.domain.port.output.UserAuthenticationRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class BeanConfiguration {
    @Bean
    public AuthenticationUseCase authenticationUseCase(
            UserAuthenticationRepository userAuthenticationRepository,
            PasswordEncoder passwordEncoder,
            JwtProvider jwtProvider
            ) {
        return new AuthenticationService(userAuthenticationRepository, passwordEncoder, jwtProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ConnectionFactory connectionFactory(
            @Value("${spring.r2dbc.username}") String username,
            @Value("${spring.r2dbc.password}") String password){
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "mssql")
                        .option(HOST, "serverusers.database.windows.net")
                        .option(PORT, 1433)
                        .option(USER, username + "@serverusers")
                        .option(PASSWORD, password)
                        .option(DATABASE, "databaseusers2")
                        .option(SSL, true)
                        .option(Option.valueOf("trustServerCertificate"), true)
                        .option(Option.valueOf("encrypt"), true)
                        .option(Option.valueOf("hostNameInCertificate"), "*.database.windows.net")
                        .build());
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // Deshabilita CSRF para APIs
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/login").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}