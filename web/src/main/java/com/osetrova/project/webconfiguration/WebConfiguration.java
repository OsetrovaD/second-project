package com.osetrova.project.webconfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.osetrova.project.controller")
@EnableWebMvc
@Import(value = {ThymeleafConfiguration.class, InternationalizationConfiguration.class})
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/PostersAndScreenshots/**")
                .addResourceLocations("classpath:/static/PostersAndScreenshots/");
    }
}
