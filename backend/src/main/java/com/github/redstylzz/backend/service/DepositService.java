package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.DepositAlreadyExistException;
import com.github.redstylzz.backend.exception.DepositDoesNotExistException;
import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.dto.DepositDTO;
import com.github.redstylzz.backend.repository.IDepositRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositService {
    private static final Log LOG = LogFactory.getLog(DepositService.class);

    private final IDepositRepository repository;

    private List<DepositDTO> getDepositsAsDTO(String userId) {
        return repository.getAllByUserId(userId).stream().map(Deposit::mapDepositToDTO).toList();
    }

    private boolean isValidDeposit(Deposit deposit) {
        return (deposit.getDescription() != null &&
                deposit.getAmount() != null &&
                deposit.getDepositDate() != null);
    }

    public List<DepositDTO> getAllDeposits(String userId) {
        return getDepositsAsDTO(userId);
    }

    public DepositDTO getDeposit(String userId, String depositId) {
        return Deposit.mapDepositToDTO(repository.getByUserIdAndDepositId(userId, depositId));
    }

    public List<DepositDTO> getLatestDeposits(String userId) {
        return repository.getAllByUserIdAndDepositDateAfterOrderByDepositDateDesc(userId,
                        LocalDateTime.now().withDayOfMonth(1).minus(Duration.ofDays(1)).toInstant(ZoneOffset.UTC))
                .stream().map(Deposit::mapDepositToDTO).toList();
    }

    public List<DepositDTO> addDeposit(String userId, Deposit deposit) {
        LOG.info("Adding deposit");

        if (!repository.existsByUserIdAndDepositId(userId, deposit.getDepositId())) {
            if (isValidDeposit(deposit)) {
                deposit.setUserId(userId);
                deposit.setSaveDate(Instant.now());
                repository.save(deposit);
            } else {
                throw new NullPointerException("Some data is null");
            }
        } else {
            throw new DepositAlreadyExistException("ID already existent");
        }

        return getDepositsAsDTO(userId);
    }

    public List<DepositDTO> changeDeposit(String userId, Deposit deposit) {
        LOG.info("Changing deposit");
        if (repository.existsByUserIdAndDepositId(userId, deposit.getDepositId())) {
            if (isValidDeposit(deposit)) {
                deposit.setUserId(userId);
                deposit.setSaveDate(Instant.now());
                repository.save(deposit);
                return getDepositsAsDTO(userId);
            } else {
                throw new NullPointerException("Some data is null");
            }
        } else {
            throw new DepositDoesNotExistException("Deposit does not exist");
        }
    }

    public List<DepositDTO> deleteDeposit(String userId, String depositId) {
        LOG.info("Deleting deposit");
        repository.deleteByUserIdAndDepositId(userId, depositId);
        return getDepositsAsDTO(userId);
    }
}
