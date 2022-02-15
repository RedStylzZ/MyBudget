package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.DepositDTO;
import com.github.redstylzz.backend.service.DepositService;
import com.github.redstylzz.backend.service.MongoUserService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {
    private static final Log LOG = LogFactory.getLog(DepositController.class);

    private final DepositService service;
    private final MongoUserService userService;

    private MongoUser getUser(Principal principal) throws ResponseStatusException {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping("{depositId}")
    public DepositDTO getDeposit(Principal principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal);
        return service.getDeposit(user.getId(), depositId);
    }

    @GetMapping
    public List<DepositDTO> getAllDeposits(Principal principal) {
        MongoUser user = getUser(principal);
        return service.getAllDeposits(user.getId());
    }

    @PostMapping
    public List<DepositDTO> addDeposit(Principal principal, @RequestBody DepositDTO dto) {
        MongoUser user = getUser(principal);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return service.addDeposit(user.getId(), deposit);
    }

    @PutMapping
    public List<DepositDTO> changeDeposit(Principal principal, @RequestBody DepositDTO dto) {
        MongoUser user = getUser(principal);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return service.changeDeposit(user.getId(), deposit);
    }

    @DeleteMapping("{depositId}")
    public List<DepositDTO> deleteDeposit(Principal principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal);
        return service.deleteDeposit(user.getId(), depositId);
    }
}
