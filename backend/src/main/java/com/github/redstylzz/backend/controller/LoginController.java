package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.MongoUserDTO;
import com.github.redstylzz.backend.service.LoginService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/login")
public class LoginController {
    private static final Log LOG = LogFactory.getLog(LoginController.class);
    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public String login(@RequestBody MongoUserDTO dto) {
        try {
            MongoUser mongoUser = MongoUser.dtoToUser(dto);
            LOG.info("Logging in user: " + mongoUser.getPassword());
            return service.login(mongoUser);
        } catch (Exception e) {
            LOG.error("Failed to create user", e);
        }
        return null;
    }


}
