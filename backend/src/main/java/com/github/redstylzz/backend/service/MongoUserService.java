package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.IMongoUserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MongoUserService implements UserDetailsService {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PRINCIPAL_IS_NULL = "Principal is null";
    private static final Log LOG = LogFactory.getLog(MongoUserService.class);
    private final IMongoUserRepository repository;


    public MongoUserService(IMongoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public MongoUser loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = repository.findMongoUserByUsername(username);
        if (user == null) {
            LOG.warn(USER_NOT_FOUND);
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

    public MongoUser getUserByPrincipal(Principal principal) throws UsernameNotFoundException {
        if (principal != null) {
            return loadUserByUsername(principal.getName());
        }
        LOG.warn(PRINCIPAL_IS_NULL);
        throw new UsernameNotFoundException(PRINCIPAL_IS_NULL);
    }
}
