package com.example.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {


    @Bean
    public JavaMailSender javaMailSender(
            @Value("${mail.host}") String host,
            @Value("${mail.port}") int port,
            @Value("${mail.username}") String username,
            @Value("${mail.password}") String password) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(mailProperties());

        return javaMailSender;
    }



    private Properties mailProperties() {
        Properties properties = new Properties();

        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        return properties;
    }

}