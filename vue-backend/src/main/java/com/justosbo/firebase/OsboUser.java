package com.justosbo.firebase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@ToString
@EqualsAndHashCode
public class OsboUser extends User {
    private final String name;
    private final AuthenticationProviderType provider;

    public OsboUser(String name, String email, AuthenticationProviderType provider) {
        super(email, "NO-PASSWORD", Collections.emptyList());
        this.name = name;
        this.provider = provider;
    }
}
