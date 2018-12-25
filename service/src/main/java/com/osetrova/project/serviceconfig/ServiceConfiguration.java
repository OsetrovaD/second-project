package com.osetrova.project.serviceconfig;

import com.osetrova.project.configuration.CachingConfiguration;
import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.osetrova.project.service")
@Import({DatabaseSpringDataConfiguration.class, AspectConfiguration.class, CachingConfiguration.class})
public class ServiceConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
