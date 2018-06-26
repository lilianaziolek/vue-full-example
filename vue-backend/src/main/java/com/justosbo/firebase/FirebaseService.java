package com.justosbo.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class FirebaseService {

    private final FirebaseAuth firebaseAuth;

    @Autowired
    public FirebaseService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseData validateTokenAndExtractData(String authToken) {
        log.debug("Attempting authentication on {}", authToken);
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdTokenAsync(authToken).get();
            FirebaseData firebaseData = FirebaseData.from(firebaseToken);
            log.debug("Firebase token found and converted to {}", firebaseData);
            return firebaseData;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to authenticate token" + authToken, e);
            return null;
        }
    }

}
