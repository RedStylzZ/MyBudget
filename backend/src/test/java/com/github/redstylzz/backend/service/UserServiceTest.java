package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private final IMongoUserRepository repository = mock(IMongoUserRepository.class);
    private final JWTService jwtService = mock(JWTService.class);
    private final MongoUserService mongoUserService = mock(MongoUserService.class);

    @Test
    void shouldReturnTokenForUserWhenCreated() {

    }
}
