package com.justosbo.firebase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class FirebaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    @Override
    public User loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        log.info("Getting user details based on firebase data {}", token);
        FirebaseData firebaseData = (FirebaseData) token.getPrincipal();
        //can be replaced with custom logic / object, also handling any authorities
        return new User(firebaseData.getName(), null, Collections.emptyList());
    }

}
