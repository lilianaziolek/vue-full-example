package com.justosbo.hello;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.justosbo.firebase.FirebaseAppHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloRoot() {
        return "I'm alive!";
    }

    @GetMapping("/api/public/hello")
    public String helloPublic(){
        return "Hello everyone!";
    }

    @GetMapping("/api/private/hello")
    public String helloPrivate(Authentication auth){
        return "Hello " + auth;
    }

    @Bean("firebaseApp")
    public FirebaseApp firebaseApp() throws IOException {
        InputStream serviceAccountCredentials = new ClassPathResource("onestopbeauty-777-firebase-adminsdk-s5zwo-2b97af0222.json").getInputStream();
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccountCredentials))
                .setDatabaseUrl("https://onestopbeauty-777.firebaseio.com")
                .build();
        return new FirebaseAppHolder(options).get();
    }
}
