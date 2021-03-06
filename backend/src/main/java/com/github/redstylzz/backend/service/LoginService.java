package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final Log LOG = LogFactory.getLog(LoginService.class);
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public String login(MongoUser user) {
        LOG.debug("Logging in user");
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            LOG.info("Successfully logged in user");
            return jwtService.createToken(user);
        } catch (AuthenticationException e) {
            LOG.warn("Invalid login credentials: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }
    }

}
