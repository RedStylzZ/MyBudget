package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceTest {

    @Spy
    private final JWTService underTest = spy(JWTService.class);
    private static final MockedStatic<Date> staticDate = mockStatic(Date.class);
    private final String actualToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUaXppYW4iLCJleHAiOjAsImlhdCI6MH0.9z7Q2p6ZlLAuABX-2fE_ouij1YjhsIAWnewZ4D2blks";
    private MongoUser user;

    @BeforeAll
    static void initAll() {
        staticDate.when(() -> Date.from(any(Instant.class))).thenReturn(new Date(0));
    }

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(underTest, "secret", "SuperSecret");
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
