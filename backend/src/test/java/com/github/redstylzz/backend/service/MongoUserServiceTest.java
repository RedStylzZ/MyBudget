package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MongoUserServiceTest {

    private final IMongoUserRepository repository = mock(IMongoUserRepository.class);
    private final MongoUserService underTest = new MongoUserService(repository);

    @Test
    void shouldThrowExceptionIfUserIsNull() {
        when(repository.findMongoUserByUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                underTest.loadUserByUsername(""));
    }

    @Test
    void shouldReturnUserOnSuccess() {
        MongoUser user = TestDataProvider.testUser();
        when(repository.findMongoUserByUsername(anyString()))
                .thenReturn(user);

        assertEquals(user, underTest.loadUserByUsername(""));
    }

    @Test
    void shouldThrowExceptionIfPrincipalIsNull() {
        assertThrows(UsernameNotFoundException.class, () -> underTest.getUserByPrincipal(null));
    }

    @Test
    void shouldReturnUser() {
        Principal principal = mock(Principal.class);
        MongoUser actualUser = TestDataProvider.testUser();
        when(principal.getName()).thenReturn(actualUser.getUsername());
        when(repository.findMongoUserByUsername(anyString())).thenReturn(actualUser);

        MongoUser user = underTest.getUserByPrincipal(principal);

        assertEquals(actualUser, user);
    }
}