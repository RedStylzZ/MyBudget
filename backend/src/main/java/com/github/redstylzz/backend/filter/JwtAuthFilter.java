package com.github.redstylzz.backend.filter;

import com.github.redstylzz.backend.service.JWTService;
import com.github.redstylzz.backend.service.MongoUserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Log LOG = LogFactory.getLog(JwtAuthFilter.class);

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JWTService jwtService, MongoUserService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        LOG.debug("JWTAuthFilter Token: " + token);
        String username = getUser(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails.getUsername()))) {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            LOG.error("ERROR while parsing token: ", e);
        }
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) return null;
        return authHeader.replace("Bearer", "").trim();
    }

    public String getUser(String token) {
        if ((token != null) && !(token.equals("undefined")) && (!token.isBlank())) {
            try {
                return jwtService.extractUsername(token);
            } catch (ExpiredJwtException e) {
                LOG.warn("Token expired: " + token);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");
            }
        }
        return null;
    }
}
