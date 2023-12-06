package com.controletotal.controletotal.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        ((authorize) -> authorize
                        // .requestMatchers("/swagger-ui/index.html").permitAll()
                        // .requestMatchers("/swagger-ui/**").permitAll()
                        // .requestMatchers("/v3/api-docs/**").permitAll()
                        // .requestMatchers("/cadastrar.html").permitAll()
                        // .requestMatchers(HttpMethod.POST,"/cadastrar").permitAll()
                        // .requestMatchers("/login.html").permitAll()
                        // .requestMatchers("/images/**", "/css/**", "/templates/**").permitAll()
                        // .requestMatchers(HttpMethod.POST,"/login").permitAll()

                        // .requestMatchers("/usuario").hasRole("ADMIN")
                        // .requestMatchers("/usuario/**").hasRole("ADMIN")
                        // // .requestMatchers(HttpMethod.POST,"/cadastrar").hasRole("ADMIN")
                        // .requestMatchers("/saida-estoque/**").hasAnyRole("ADMIN", "ALMOXARIFE")
                        // .requestMatchers("/itens/cadastrar").hasAnyRole("ADMIN", "ALMOXARIFE")
                        // .requestMatchers("/itens/atualizar").hasAnyRole("ADMIN", "ALMOXARIFE")
                        // .requestMatchers("/itens/deletar/**").hasAnyRole("ADMIN", "ALMOXARIFE")
                        // .requestMatchers("/fornecedores/**").hasAnyRole("ADMIN", "ALMOXARIFE")

                        .anyRequest().permitAll()
                )
                // .formLogin(form -> form
                //     .loginPage("/login")
                //     .usernameParameter("email")
                //     .passwordParameter("senha")
                //     .defaultSuccessUrl("/")
                //     .permitAll()
                // )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
