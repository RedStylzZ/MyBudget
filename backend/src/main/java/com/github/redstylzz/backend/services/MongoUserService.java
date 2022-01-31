package com.github.redstylzz.backend.services;

import com.github.redstylzz.backend.models.MongoUser;
import com.github.redstylzz.backend.repositories.IMongoUserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserService implements UserDetailsService {
    private static final Log LOG = LogFactory.getLog(MongoUserService.class);
    public static final String ROLE_ADMIN = "ADMIN";
    private final IMongoUserRepository repository;


    public MongoUserService(IMongoUserRepository repository) {
        this.repository = repository;
    }

    public UserDetails loadUserByID(String id) {
        MongoUser user = repository.findMongoUserById(id);
        LOG.debug("Fetching user from id: " + id);
        if (user == null) {
            LOG.warn("Could not find user: " + id);
            throw new UsernameNotFoundException("User not found");
        }
        LOG.debug("Found user: " + user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = repository.findMongoUserByUsername(username);
        LOG.debug("Fetching user: " + username);
        if (user == null) {
            LOG.warn("Could not find user: " + username);
            throw new UsernameNotFoundException("User not found");
        }
        LOG.debug("Found user: " + user);
        return user;
    }
}
