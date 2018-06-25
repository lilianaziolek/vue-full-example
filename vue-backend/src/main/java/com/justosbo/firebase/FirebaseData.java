package com.justosbo.firebase;

import com.google.firebase.auth.FirebaseToken;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
@Builder
public class FirebaseData {
    @NonNull
    private final String email;
    @NonNull
    private final String name;
    @NonNull
    private final String uid;
    @NonNull
    private final AuthenticationProviderType provider;

    public static FirebaseData from(FirebaseToken firebaseToken) {
        if (firebaseToken.getEmail() == null) {
            throw new IllegalArgumentException("Token verified, but does not contain email, uid: " + firebaseToken.getUid());
        }
        AuthenticationProviderType providerType = toAuthenticationProviderType(firebaseToken);
        return FirebaseData.builder()
                .email(firebaseToken.getEmail())
                .uid(firebaseToken.getUid())
                .name(firebaseToken.getName())
                .provider(providerType)
                .build();
    }

    private static AuthenticationProviderType toAuthenticationProviderType(FirebaseToken firebaseToken) {
        String authProviderStr = (String) ((Map) firebaseToken.getClaims().get("firebase")).get("sign_in_provider");
        switch (authProviderStr) {
        case "facebook.com":
            return AuthenticationProviderType.FACEBOOK;
        case "google.com":
            return AuthenticationProviderType.GOOGLE;
        case "twitter.com":
            return AuthenticationProviderType.TWITTER;
        case "password":
            return AuthenticationProviderType.USERNAME_PASSWORD;
        }
        throw new IllegalArgumentException("Unknown provider: " + authProviderStr + " for token belonging to: " + firebaseToken.getEmail());
    }

}
