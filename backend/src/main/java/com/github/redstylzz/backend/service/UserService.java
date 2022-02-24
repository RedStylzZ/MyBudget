package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.MongoUserDTO;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Log LOG = LogFactory.getLog(UserService.class);
    private static final String USER_ALREADY_EXISTS = "User already exists";
    private static final String NOT_ADMIN = "User is not Admin";
    private final IMongoUserRepository repository;
    private final JWTService jwtService;

    public boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals(MongoUserService.ROLE_ADMIN));
    }

    public String addUser(MongoUser admin, MongoUserDTO user) throws ResponseStatusException {
        if (isAdmin(admin.getAuthorities())) {
            if (repository.findMongoUserByUsername(user.getUsername()) == null) {
                MongoUser newUser = MongoUser.builder()
                        .username(user.getUsername())
                        .password(new Argon2PasswordEncoder().encode(user.getPassword()))
                        .rights(user.getRights())
                        .accountNonLocked(true)
                        .accountNonExpired(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build();

                repository.save(newUser);
                LOG.info("Added new User");
                return jwtService.createToken(newUser);
            }
            LOG.warn(USER_ALREADY_EXISTS);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS);
        }
        LOG.warn(NOT_ADMIN);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, NOT_ADMIN);
    }
}
