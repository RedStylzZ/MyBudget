package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.filter.JwtAuthFilter;
import com.github.redstylzz.backend.model.MongoUser;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final Log LOG = LogFactory.getLog(LoginService.class);
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final JwtAuthFilter authFilter;

    public boolean checkLogin(HttpServletRequest request) {
        String token = authFilter.getToken(request);
        return authFilter.getUser(token) != null;
    }

    public String login(MongoUser user) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            LOG.debug("Successfully logged in user: " + user.getUsername());
            return jwtService.createToken(user);
        } catch (AuthenticationException e) {
            LOG.warn("Login invalid credentials: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }
    }

}
