package com.justosbo;

import com.justosbo.firebase.FirebasePreAuthFilter;
import com.justosbo.firebase.FirebaseService;
import com.justosbo.firebase.FirebaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Arrays.asList;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final FirebaseUserDetailsService userDetailsService;
    private final FirebaseService firebaseService;

    @Autowired
    public WebSecurityConfig(FirebaseUserDetailsService userDetailsService, FirebaseService firebaseService) {
        this.userDetailsService = userDetailsService;
        this.firebaseService = firebaseService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FirebasePreAuthFilter firebasePreAuthFilter = new FirebasePreAuthFilter(this.firebaseService);
        firebasePreAuthFilter.setAuthenticationManager(authenticationManager());

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(asList("http://localhost:3000", "http://localhost:8080", "http://localhost:8081"));
        corsConfiguration.setAllowedMethods(asList("*"));
        corsConfiguration.setAllowedHeaders(asList("*"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        // @formatter:off
		http
            .addFilterAfter(firebasePreAuthFilter, AbstractPreAuthenticatedProcessingFilter.class)
            .authorizeRequests()
                .antMatchers("/", "/favicon.ico","/robots.txt", "/api/public/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
            //note: logout has to be a post
            .and().logout().logoutSuccessUrl("/")
            .and().cors().configurationSource(corsConfigurationSource)
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        ;
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(this.userDetailsService);
        preAuthenticatedAuthenticationProvider.setThrowExceptionWhenTokenRejected(true);
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
    }
}
