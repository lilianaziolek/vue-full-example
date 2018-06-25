package com.justosbo.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    @Bean("firebaseApp")
    @Profile("local")
    public FirebaseApp firebaseApp() throws IOException {
        InputStream serviceAccountCredentials = new ClassPathResource("onestopbeauty-777-firebase-adminsdk-s5zwo-2b97af0222.json").getInputStream();
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccountCredentials))
                .setDatabaseUrl("https://onestopbeauty-777.firebaseio.com")
                .build();
        return new FirebaseAppHolder(options).get();
    }

    @Bean("firebaseApp")
    @Profile("appengine")
    public FirebaseApp firebaseAppAppengine() throws IOException {
        //if creds don't work, try troubleshooting from here: https://firebase.google.com/docs/admin/setup
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://onestopbeauty-777.firebaseio.com")
                .build();
        return new FirebaseAppHolder(options).get();
    }

    @Bean
    @DependsOn("firebaseApp")
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }

}