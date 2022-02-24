package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> getAllByUserIdAndCategoryIdOrderByPayDateDesc(String userId, String categoryId);

    List<Payment> getAllByUserIdAndPayDateAfterOrderByPayDateDesc(String userId, Instant date);

    List<Payment> getAllByUserIdAndCategoryIdAndPayDateAfterOrderByPayDateDesc(String userId, String categoryId, Instant date);

    Payment getByUserIdAndCategoryIdAndPaymentId(String userId, String categoryId, String paymentId);

    boolean existsByPaymentId(String paymentId);

    void deleteByPaymentId(String paymentId);

    void deleteAllByUserIdAndCategoryId(String userId, String categoryId);

}
