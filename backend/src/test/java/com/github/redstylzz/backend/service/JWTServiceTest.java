package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class JWTServiceTest {

    private static final MockedStatic<Date> staticDate = mockStatic(Date.class);
    private final String actualToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0IiwiZXhwIjowLCJpYXQiOjB9.9o_lZ1UCyd3l-kwbjtQLFRunqZouMK7J0DiZSp2dFQo";
    @Autowired
    private JWTService underTest;
    private MongoUser user;

    @BeforeAll
    static void initAll() {
        staticDate.when(() -> Date.from(any(Instant.class))).thenReturn(new Date(0));
    }

    @BeforeEach
    void init() {
        user = TestDataProvider.testUser();
    }

    @Test
    void shouldReturnTokenForUser() {
        String token = underTest.createToken(user);

        assertEquals(actualToken, token);
    }

    @Test
    void shouldReturnTrueIfTokenIsValid() {
        String name = user.getUsername();

        assertTrue(underTest.validateToken(actualToken, name));
    }

}
