package com.hsxy.dessert.config;

import com.hsxy.dessert.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity

public class WebSecurityConfig {
    @Autowired
    AppUserDetailsService appUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(headers-> headers
                .frameOptions(frame->frame
                        .sameOrigin())
        );

        http.userDetailsService(appUserDetailsService);
        http.authorizeHttpRequests(
                authorize->authorize
                        .requestMatchers("/css/**", "/img/**", "/js/**", "/photo/**")
                        .permitAll()
                        .requestMatchers("/","/login","/register")
                        .permitAll()
                        .requestMatchers("/categories","/category/*").hasRole("admin")
                        .requestMatchers("/desserts").hasAnyRole("admin","normal")
                        .requestMatchers("/dessert/*").hasRole("admin")
                        .requestMatchers("/releaseNew/*").hasAnyRole("admin","normal")
                        .anyRequest().authenticated()
        );
        http.formLogin(formlogin->formlogin
                .loginPage("/login")
                .defaultSuccessUrl("/login/success",true)
                .permitAll()
        );
        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        http.exceptionHandling(exception->exception
                .accessDeniedPage("/403")
        );
        return http.build();
    }
}