package com.justosbo;

import com.justosbo.firebase.OsboUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@RestController
@Slf4j
public class LoginController {

    @GetMapping("/api/public/loginStatus")
    public Map<String, String> user(Authentication authentication) {
        Map<String, String> map = new LinkedHashMap<>();
        if (authentication != null) {
            String email = authentication.getName();
            map.put("email", email);
            OsboUser user = (OsboUser) authentication.getPrincipal();
            log.debug("User profile for logged in user: {}", user);
            map.put("name", user.getName());
            map.put("provider", user.getProvider().name());
        }
        return map;
    }

}

