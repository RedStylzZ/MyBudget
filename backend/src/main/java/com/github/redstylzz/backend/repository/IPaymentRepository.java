package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> getAllByUserIDAndCategoryIDOrderByPayDateDesc(String userID, String categoryID);

    List<Payment> getAllByUserIDAndPayDateAfterOrderByPayDateDesc(String userID, Instant date);

    List<Payment> getAllByUserIDAndCategoryIDAndPayDateAfterOrderByPayDateDesc(String userID, String categoryID, LocalDateTime date);

    Payment getByUserIDAndCategoryIDAndPaymentID(String userID, String categoryID, String paymentID);

    boolean existsByPaymentID(String paymentID);

    void deleteByPaymentID(String paymentID);

    void deleteAllByUserIDAndCategoryID(String userID, String categoryID);

}
