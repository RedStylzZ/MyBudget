package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.model.dto.MongoUserDTO;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final IMongoUserRepository repository = mock(IMongoUserRepository.class);
    private final JWTService jwtService = mock(JWTService.class);
    private final UserService underTest = new UserService(repository, jwtService);
    private MongoUser user;
    private MongoUserDTO dto;

    @BeforeEach
    void init() {
        user = TestDataProvider.testUser();
        dto = TestDataProvider.testUserDTO();
    }

    @Test
    void shouldThrowExceptionIfUserIsNotAdmin() {
        assertThrows(ResponseStatusException.class, () -> underTest.addUser(user, dto));
    }

    @Test
    void shouldReturnTokenForUserWhenCreated() {
        user.setRights(List.of("ADMIN"));
        when(jwtService.createToken(any(MongoUser.class))).thenReturn(user.getId());

        String token = underTest.addUser(user, dto);

        verify(repository).save(any(MongoUser.class));
        assertEquals(user.getId(), token);
    }

    @Test
    void shouldThrowExceptionIfUserAlreadyExists() {
        user.setRights(List.of("ADMIN"));
        when(repository.findMongoUserByUsername(anyString())).thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> underTest.addUser(user, dto));
    }

}
