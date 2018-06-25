package com.justosbo.firebase;

import com.google.api.client.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class FirebasePreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    public final static String TOKEN_HEADER = "X-Firebase-Auth";

    private FirebaseService firebaseService;

    public FirebasePreAuthFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        log.debug("Checking if token exists on session " + request.getSession().getId());
        final String authToken = request.getHeader(TOKEN_HEADER);
        if (Strings.isNullOrEmpty(authToken)) {
            log.trace("No {} token found", TOKEN_HEADER);
            return null;
        }
        log.info("Validating token {} on request {}", authToken, request.getSession().getId());
        return firebaseService.validateTokenAndExtractData(authToken);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

}
