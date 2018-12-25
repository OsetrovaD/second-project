package com.osetrova.project.webconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class ThymeleafConfiguration {

    private static final String PREFIX = "/WEB-INF/templates/";
    private static final String SUFFIX = ".html";

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setCharacterEncoding(UTF_8.name());
        resolver.setCacheable(false);
        resolver.setPrefix(PREFIX);
        resolver.setSuffix(SUFFIX);

        return resolver;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(new Java8TimeDialect());
        engine.addDialect(springSecurityDialect());

        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setCharacterEncoding(UTF_8.name());
        resolver.setTemplateEngine(engine());

        return resolver;
    }
}
