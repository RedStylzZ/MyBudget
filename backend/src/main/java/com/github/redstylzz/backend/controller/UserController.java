package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.MongoUserDTO;
import com.github.redstylzz.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final Log LOG = LogFactory.getLog(UserController.class);

    private final UserService userService;

    private MongoUser getUser(UsernamePasswordAuthenticationToken principal) throws ResponseStatusException {
        try {
            return (MongoUser) principal.getPrincipal();
        } catch (Exception e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }
    
    @GetMapping("/admin")
    public boolean isAdmin(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return userService.isAdmin(user.getAuthorities());
    }

    @PostMapping
    public boolean addUser(UsernamePasswordAuthenticationToken principal, @RequestBody MongoUserDTO user) {
        try {
            MongoUser admin = getUser(principal);
            userService.addUser(admin, user);
            return true;
        } catch (ResponseStatusException e) {
            return false;
        }
    }

}
