package com.controletotal.controletotal.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    // @Autowired
    // SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        ((authorize) -> authorize
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/cadastrar.html").permitAll()
                        .requestMatchers(HttpMethod.POST,"/cadastrar").permitAll()
                        .requestMatchers("/login.html").permitAll()
                        .requestMatchers("/images/**", "/css/**", "/templates/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/login").permitAll()

                        .requestMatchers("/usuario").hasRole("ADMIN")
                        .requestMatchers("/usuario/**").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.POST,"/cadastrar").hasRole("ADMIN")
                        .requestMatchers("/saida-estoque/**").hasAnyRole("ADMIN", "ALMOXARIFE")
                        .requestMatchers("/itens/cadastrar").hasAnyRole("ADMIN", "ALMOXARIFE")
                        .requestMatchers("/itens/atualizar").hasAnyRole("ADMIN", "ALMOXARIFE")
                        .requestMatchers("/itens/deletar/**").hasAnyRole("ADMIN", "ALMOXARIFE")
                        .requestMatchers("/fornecedores/**").hasAnyRole("ADMIN", "ALMOXARIFE")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("senha")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    // .loginProcessingUrl("/login")
                    // .defaultSuccessUrl("/swagger-ui/index.html")
                )
                // .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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
