package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final IPaymentRepository paymentRepo;
    private final ICategoryRepository categoryRepository;

    public PaymentService(IPaymentRepository paymentRepo, ICategoryRepository categoryRepository) {
        this.paymentRepo = paymentRepo;
        this.categoryRepository = categoryRepository;
    }

    private boolean categoryNotExistent(String userID, String categoryID) {
        return !categoryRepository.existsByUserIDAndCategoryID(userID, categoryID);
    }


    public List<Payment> getPayments(String userID, String categoryID) {
        return paymentRepo.getAllByUserIDAndCategoryID(userID, categoryID);
    }

    public List<Payment> addPayment(String userID, Payment payment) throws CategoryDoesNotExistException {
        if (categoryNotExistent(userID, payment.getCategoryID())) {
            payment.setPaymentID(UUID.randomUUID().toString());
            payment.setUserID(userID);
            payment.setSaveDate(new Date());
            paymentRepo.save(payment);
        } else {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return paymentRepo.getAllByUserIDAndCategoryID(userID, payment.getCategoryID());
    }

    public List<Payment> deletePayment(String userID, Payment payment) {
        if (categoryNotExistent(userID, payment.getCategoryID())) {
            paymentRepo.deleteByPaymentID(payment.getPaymentID());
        } else {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return paymentRepo.getAllByUserIDAndCategoryID(userID, payment.getCategoryID());
    }
}
