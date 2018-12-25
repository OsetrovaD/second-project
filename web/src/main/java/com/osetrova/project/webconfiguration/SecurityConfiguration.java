package com.osetrova.project.webconfiguration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] ADMIN_PAGES =
            {       "/all-orders",
                    "/change-number-in-storage",
                    "/add-new-game",
                    "/all-users",
                    "/choose-date-period",
                    "/all-users",
                    "/choose-date-period",
                    "/order-search-result",
                    "/change-order-condition",
                    "/change-order-delivery-date"
            };
    private static final String[] ANY_USER_PAGES = {"/", "/login", "/register"};
    private static final String[] SUPER_ADMIN_PAGES = {"/change-role-to-admin", "/change-role-to-user", "/change-salary", "/set-salary"};

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        return new CharacterEncodingFilter(UTF_8.name());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(encodingFilter(), CsrfFilter.class);

        http
                .authorizeRequests()
                    .antMatchers(ADMIN_PAGES)
                        .hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                    .antMatchers(SUPER_ADMIN_PAGES)
                        .hasAnyAuthority("SUPER_ADMIN")
                    .antMatchers(ANY_USER_PAGES)
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
                    .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                    .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
