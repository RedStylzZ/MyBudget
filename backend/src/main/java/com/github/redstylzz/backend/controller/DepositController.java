package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.DepositDTO;
import com.github.redstylzz.backend.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.github.redstylzz.backend.model.MongoUser.getUser;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {
    private static final Log LOG = LogFactory.getLog(DepositController.class);

    private final DepositService service;
    
    @GetMapping("/latest")
    public List<DepositDTO> getLatestDeposits(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getLatestDeposits(user.getId());
    }

    @GetMapping("{depositId}")
    public DepositDTO getDeposit(UsernamePasswordAuthenticationToken principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal, LOG);
        return service.getDeposit(user.getId(), depositId);
    }

    @GetMapping
    public List<DepositDTO> getAllDeposits(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal, LOG);
        return service.getAllDeposits(user.getId());
    }

    @PostMapping
    public List<DepositDTO> addDeposit(UsernamePasswordAuthenticationToken principal, @RequestBody DepositDTO dto) {
        MongoUser user = getUser(principal, LOG);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return service.addDeposit(user.getId(), deposit);
    }

    @PutMapping
    public List<DepositDTO> changeDeposit(UsernamePasswordAuthenticationToken principal, @RequestBody DepositDTO dto) {
        MongoUser user = getUser(principal, LOG);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return service.changeDeposit(user.getId(), deposit);
    }

    @DeleteMapping("{depositId}")
    public List<DepositDTO> deleteDeposit(UsernamePasswordAuthenticationToken principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal, LOG);
        return service.deleteDeposit(user.getId(), depositId);
    }
}
