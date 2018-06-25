package com.justosbo.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseAppHolder {
    private static FirebaseApp firebaseApp;

    public FirebaseAppHolder(FirebaseOptions options) {
        synchronized (FirebaseAppHolder.class) {
            if (firebaseApp == null) {
                firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                log.warn("Firebase app already existed during bean creation. Discarding and creating new instance.");
                firebaseApp.delete();
                firebaseApp = FirebaseApp.initializeApp(options);
            }
        }

    }

    public FirebaseApp get() {
        synchronized (FirebaseAppHolder.class) {
            return firebaseApp;
        }
    }
}
