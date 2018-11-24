package com.osetrova.project.webconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_PAGES = {"/all-orders", "/add-to-storage", "/add-game", "/all-users", "/choose-date-period"};
    private static final String[] ALL_USERS_PAGES = {"/", "/login", "/register"};

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(ADMIN_PAGES)
                    .hasAnyAuthority("ADMIN")
                .antMatchers(ALL_USERS_PAGES)
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user", true)
            .and()
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .userDetailsService(userDetailsService);
    }
}
