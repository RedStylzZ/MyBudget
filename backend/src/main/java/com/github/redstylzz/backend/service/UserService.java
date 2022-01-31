package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Log LOG = LogFactory.getLog(UserService.class);
    private final IMongoUserRepository repository;
    private final JWTService jwtService;
    private final MongoUserService mongoUserService;

    public UserService(IMongoUserRepository repository, JWTService jwtService, MongoUserService mongoUserService) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.mongoUserService = mongoUserService;
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals(MongoUserService.ROLE_ADMIN));
    }

    public String addUser(Principal principal, UserDetails user) {
        try {
            if (principal != null) {
                final UserDetails mongoUser = mongoUserService.loadUserByUsername(principal.getName());
                if (isAdmin(mongoUser.getAuthorities())) {
                    MongoUser newUser = MongoUser.builder()
                            .id(UUID.randomUUID().toString())
                            .username(user.getUsername())
                            .password(new Argon2PasswordEncoder().encode(user.getPassword()))
                            .rights(List.of("USER"))
                            .accountNonLocked(true)
                            .accountNonExpired(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
                            .build();

                    repository.save(newUser);
                    LOG.info("Added new User: " + newUser);
                    return jwtService.createToken(newUser);
                }
            } else {
                LOG.warn("Principal is null");
            }
        } catch (Exception e) {
            LOG.warn("Add Account Exception:", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Input");
        }
        return null;
    }
}
