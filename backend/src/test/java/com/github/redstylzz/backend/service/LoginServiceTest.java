package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.filter.JwtAuthFilter;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    private final AuthenticationManager authManager = mock(AuthenticationManager.class);
    private final JWTService jwtService = mock(JWTService.class);
    private final JwtAuthFilter authFilter = mock(JwtAuthFilter.class);
    private final LoginService underTest = new LoginService(authManager, jwtService, authFilter);

    @Test
    void shouldThrowErrorIfLoginFails() {
        MongoUser user = TestDataProvider.testUser();
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(mock(AuthenticationException.class));

        assertThrows(ResponseStatusException.class, () -> underTest.login(user));
    }

    @Test
    void shouldReturnNewUserToken() {
        MongoUser user = TestDataProvider.testUser();
        when(jwtService.createToken(any(MongoUser.class))).thenReturn(user.getId());

        assertEquals(user.getId(), underTest.login(user));

    }
}