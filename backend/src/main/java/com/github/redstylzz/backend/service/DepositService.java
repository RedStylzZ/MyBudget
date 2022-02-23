package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.DepositAlreadyExistException;
import com.github.redstylzz.backend.exception.DepositDoesNotExistException;
import com.github.redstylzz.backend.model.Deposit;
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

    private boolean isValidDeposit(Deposit deposit) {
        return (deposit.getDescription() != null &&
                deposit.getAmount() != null &&
                deposit.getDepositDate() != null);
    }

    public List<Deposit> getDepositsFrom(String userId) {
        return repository.getAllByUserId(userId);
    }

    public Deposit getDepositFrom(String userId, String depositId) {
        return repository.getByUserIdAndDepositId(userId, depositId);
    }

    public List<Deposit> getLatestDeposits(String userId) {
        return repository.getAllByUserIdAndDepositDateAfterOrderByDepositDateDesc(userId,
                LocalDateTime.now().withDayOfMonth(1).minus(Duration.ofDays(1)).toInstant(ZoneOffset.UTC));
    }

    public List<Deposit> addDeposit(String userId, Deposit deposit) {
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

        return getDepositsFrom(userId);
    }

    public List<Deposit> changeDeposit(String userId, Deposit deposit) {
        LOG.info("Changing deposit");
        if (repository.existsByUserIdAndDepositId(userId, deposit.getDepositId())) {
            if (isValidDeposit(deposit)) {
                deposit.setUserId(userId);
                deposit.setSaveDate(Instant.now());
                repository.save(deposit);
                return getDepositsFrom(userId);
            } else {
                throw new NullPointerException("Description, Amount or DepositDate is null");
            }
        } else {
            throw new DepositDoesNotExistException("Deposit does not exist");
        }
    }

    public List<Deposit> deleteDeposit(String userId, String depositId) {
        LOG.info("Deleting deposit");
        repository.deleteByUserIdAndDepositId(userId, depositId);
        return getDepositsFrom(userId);
    }
}
