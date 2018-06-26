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
        //could expand with fetching more details from database, authorities etc.
        return new OsboUser(firebaseData.getName(),firebaseData.getEmail(), firebaseData.getProvider());
    }

}
