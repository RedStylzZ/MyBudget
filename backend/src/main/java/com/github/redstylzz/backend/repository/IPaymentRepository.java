package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> getAllByUserIDAndCategoryID(String userID, String categoryID);

    boolean existsByPaymentID(String paymentID);

    void deleteByPaymentID(String paymentID);

    void deleteAllByUserIDAndCategoryID(String userID, String categoryID);

    List<Payment> getAllByUserIDAndPayDateAfter(String userID, LocalDateTime date);

    List<Payment> getAllByUserIDAndCategoryIDAndPayDateAfter(String userID, String categoryID, LocalDateTime date);

    Payment getByUserIDAndCategoryIDAndPaymentID(String userID, String categoryID, String paymentID);

}
