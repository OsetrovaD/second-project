package com.osetrova.project.serviceconfig;

import com.osetrova.project.configuration.DatabaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.osetrova.project")
@Import(DatabaseConfiguration.class)
public class ServiceConfiguration {
}
