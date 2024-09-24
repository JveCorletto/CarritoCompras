package com.ufg.parcial_2.Security;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter();
        customAuthFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/perform_login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("usuario")
                        .passwordParameter("contrasenia")

                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .addFilterAt(customAuthFilter, UsernamePasswordAuthenticationFilter.class)  // Register custom filter
                .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/setLogin").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/perform_login")
//                        .usernameParameter("usuario")
//                        .passwordParameter("contrasenia")
//
//                        .defaultSuccessUrl("/home", true)
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//                .logout(LogoutConfigurer::permitAll);
//        return http.build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}