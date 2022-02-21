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
    private static final Log LOG = LogFactory.getLog(MongoUserService.class);
    private final IMongoUserRepository repository;


    public MongoUserService(IMongoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public MongoUser loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = repository.findMongoUserByUsername(username);
        if (user == null) {
            LOG.warn("Could not find user");
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public MongoUser getUserByPrincipal(Principal principal) throws UsernameNotFoundException {
        if (principal != null) {
            return loadUserByUsername(principal.getName());
        }
        LOG.warn("Principal is null");
        throw new UsernameNotFoundException("Principal is null");
    }
}
