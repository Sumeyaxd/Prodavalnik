package com.prodavalnik.prodavalnik.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/about", "/comments", "/suppliers",
                                "/shops/plovdiv", "/shops/burgas", "/shops/sofia", "/", "/users/login", "/users/register","/offers/add-offer").permitAll()
                        .requestMatchers( "/offers/all/delete-offer/**", "/orders", "/suppliers/add-supplier").hasRole("ADMINISTRATOR")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/users/login").permitAll()
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/users/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}