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

    @Autowired
    private JWTService underTest;
    private static final MockedStatic<Date> staticDate = mockStatic(Date.class);
    private final String actualToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUaXppYW4iLCJleHAiOjAsImlhdCI6MH0.9z7Q2p6ZlLAuABX-2fE_ouij1YjhsIAWnewZ4D2blks";
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
