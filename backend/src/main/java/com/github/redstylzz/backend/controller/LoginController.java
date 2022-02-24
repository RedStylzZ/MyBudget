package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.LoginDTO;
import com.github.redstylzz.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth/login")
@RequiredArgsConstructor
public class LoginController {
    private static final Log LOG = LogFactory.getLog(LoginController.class);
    private final LoginService service;

    @PostMapping()
    public String login(@RequestBody LoginDTO dto) {
        try {
            MongoUser mongoUser = MongoUser.dtoToUser(dto);
            LOG.debug("Logging in user");
            return service.login(mongoUser);
        } catch (Exception e) {
            LOG.error("Failed to login user", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong Credentials");
        }
    }


}
