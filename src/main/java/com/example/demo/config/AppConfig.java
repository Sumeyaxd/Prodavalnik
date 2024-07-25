package com.example.demo.config;
<<<<<<< HEAD
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
=======

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

>>>>>>> origin/main
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
<<<<<<< HEAD
=======

>>>>>>> origin/main
}
