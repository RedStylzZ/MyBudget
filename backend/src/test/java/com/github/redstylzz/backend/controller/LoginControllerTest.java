package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.LoginDTO;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.github.redstylzz.backend.model.TestDataProvider.testUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IMongoUserRepository mongoUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final WebClient webClient = WebClient.create();

    @Test
    void shouldReturnTokenOnSuccessfulLogin() {
        MongoUser user = testUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        when(mongoUserRepository.findMongoUserByUsername("Test")).thenReturn(user);
        LoginDTO loginData = new LoginDTO("Test", "TestPassword");

        ResponseEntity<String> login = webClient.post()
                .uri("http://localhost:"+port+"/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginData)
                .retrieve()
                .toEntity(String.class)
                .block();

        //THEN
        assert login != null;
        assertEquals(HttpStatus.OK, login.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWithWrongCredentials() {
        LoginDTO loginData = new LoginDTO("Test", "TestPassword");

        ResponseEntity<String> login = webClient.post()
                .uri("http://localhost:"+port+"/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginData)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.FORBIDDEN),
                        clientResponse -> Mono.empty())
                .toEntity(String.class)
                .block();

        assert login != null;
        assertEquals(HttpStatus.FORBIDDEN, login.getStatusCode());
        System.out.println(login.getStatusCode());
    }


}