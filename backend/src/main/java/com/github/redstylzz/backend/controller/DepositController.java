package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.DepositCreationDTO;
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

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {
    private static final Log LOG = LogFactory.getLog(DepositController.class);

    private final DepositService service;

    private MongoUser getUser(UsernamePasswordAuthenticationToken principal) throws ResponseStatusException {
        try {
            return (MongoUser) principal.getPrincipal();
        } catch (Exception e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    public List<DepositDTO> convertToDTO(List<Deposit> series) {
        LOG.info("Get series");
        return series.stream().map(Deposit::mapDepositToDTO).toList();
    }

    @GetMapping("/latest")
    public List<DepositDTO> getLatestDeposits(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.getLatestDeposits(user.getId()));
    }

    @GetMapping("{depositId}")
    public DepositDTO getDeposit(UsernamePasswordAuthenticationToken principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal);
        return Deposit.mapDepositToDTO(service.getDepositFrom(user.getId(), depositId));
    }

    @GetMapping
    public List<DepositDTO> getAllDeposits(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.getDepositsFrom(user.getId()));
    }

    @PostMapping
    public List<DepositDTO> addDeposit(UsernamePasswordAuthenticationToken principal, @RequestBody DepositCreationDTO dto) {
        MongoUser user = getUser(principal);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return convertToDTO(service.addDeposit(user.getId(), deposit));
    }

    @PutMapping
    public List<DepositDTO> changeDeposit(UsernamePasswordAuthenticationToken principal, @RequestBody DepositDTO dto) {
        MongoUser user = getUser(principal);
        Deposit deposit = Deposit.mapDTOtoDeposit(dto);
        return convertToDTO(service.changeDeposit(user.getId(), deposit));
    }

    @DeleteMapping("{depositId}")
    public List<DepositDTO> deleteDeposit(UsernamePasswordAuthenticationToken principal, @PathVariable String depositId) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.deleteDeposit(user.getId(), depositId));
    }
}
