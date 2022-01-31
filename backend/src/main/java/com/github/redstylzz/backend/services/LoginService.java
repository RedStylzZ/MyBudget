package com.github.redstylzz.backend.services;

import com.github.redstylzz.backend.models.MongoUser;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {
    private static final Log LOG = LogFactory.getLog(LoginService.class);
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public LoginService(AuthenticationManager authManager, JWTService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public String login(MongoUser user) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            LOG.info("Successfully logged in user: " + user.getUsername());
            return jwtService.createToken(user);
        } catch (AuthenticationException e) {
            LOG.warn("Login invalid credentials: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }
    }

}
