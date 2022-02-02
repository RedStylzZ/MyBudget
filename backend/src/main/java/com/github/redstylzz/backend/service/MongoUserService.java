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
    private static final Log LOG = LogFactory.getLog(MongoUserService.class);
    public static final String ROLE_ADMIN = "ADMIN";
    private final IMongoUserRepository repository;


    public MongoUserService(IMongoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public MongoUser loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = repository.findMongoUserByUsername(username);
        LOG.debug("Fetching user: " + username);
        if (user == null) {
            LOG.warn("Could not find user: " + username);
            throw new UsernameNotFoundException("User not found");
        }
        LOG.debug("Found user: " + user);
        return user;
    }

    public MongoUser getUserByPrincipal(Principal principal) throws UsernameNotFoundException {
        if (principal != null) {
            return loadUserByUsername(principal.getName());
        }
        LOG.debug("Principal is null");
        throw new UsernameNotFoundException("Principal is null");
    }
}
