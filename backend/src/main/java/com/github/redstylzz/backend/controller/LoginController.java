package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.MongoUserDTO;
import com.github.redstylzz.backend.service.LoginService;
import com.github.redstylzz.backend.service.MongoUserService;
import com.github.redstylzz.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class LoginController {
    private static final Log LOG = LogFactory.getLog(LoginController.class);
    private final LoginService service;
    private final MongoUserService userService;

    private MongoUser getUser(Principal principal) throws ResponseStatusException {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody MongoUserDTO dto) {
        try {
            MongoUser mongoUser = MongoUser.dtoToUser(dto);
            LOG.info("Logging in user: " + mongoUser.getUsername());
            return service.login(mongoUser);
        } catch (Exception e) {
            LOG.error("Failed to create user", e);
        }
        return null;
    }

    @GetMapping("/admin")
    public boolean isAdmin(Principal principal) {
        MongoUser user = getUser(principal);
        return service.isAdmin(user);
    }


}
