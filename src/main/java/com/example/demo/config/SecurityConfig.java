package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
            .inMemoryAuthentication()
            .withUser("admin")
            .password(encoder.encode("admin"))
            .roles("FISHERMAN")
            .and()
            .withUser("sales")
            .password(encoder.encode("sales"))
            .roles("SALESMAN");
    }
    //userdetailsService i model user, role w bazie danych

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and()
            .csrf().disable()
            .httpBasic();
        //obsluzyc brak mozliwosci wejscia na h2
    }
}