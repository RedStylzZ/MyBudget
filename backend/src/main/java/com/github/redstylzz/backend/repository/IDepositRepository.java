package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Deposit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface IDepositRepository extends MongoRepository<Deposit, String> {

    List<Deposit> getAllByUserId(String userId);

    List<Deposit> getAllByUserIdAndDepositDateAfterOrderByDepositDateDesc(String userId, Instant date);

    Deposit getByUserIdAndDepositId(String userId, String depositId);

    void deleteByUserIdAndDepositId(String userId, String depositId);

    boolean existsByUserIdAndDepositId(String userId, String depositId);

}
