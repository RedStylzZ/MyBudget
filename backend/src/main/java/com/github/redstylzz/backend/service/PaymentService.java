package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
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

    private boolean categoryExistent(String userID, String categoryID) {
        return categoryRepository.existsByUserIDAndCategoryID(userID, categoryID);
    }

    private boolean paymentExists(String paymentID) {
        return paymentRepo.existsByPaymentID(paymentID);
    }


    public List<Payment> getPayments(String userID, String categoryID) {
        return paymentRepo.getAllByUserIDAndCategoryID(userID, categoryID);
    }

    public List<Payment> addPayment(String userID, Payment payment) throws CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            payment.setPaymentID(UUID.randomUUID().toString());
            payment.setUserID(userID);
            payment.setSaveDate(new Date());
            paymentRepo.save(payment);
        } else {
            throw new CategoryDoesNotExistException();
        }
        return paymentRepo.getAllByUserIDAndCategoryID(userID, payment.getCategoryID());
    }

    public List<Payment> deletePayment(String userID, Payment payment) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            paymentRepo.deleteByPaymentID(payment.getPaymentID());
        } else {
            throw new CategoryDoesNotExistException();
        }
        return paymentRepo.getAllByUserIDAndCategoryID(userID, payment.getCategoryID());
    }

    public List<Payment> changePayment(String userID, Payment payment) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            if (paymentExists(payment.getPaymentID())) {
                payment.setSaveDate(new Date());
                paymentRepo.save(payment);
            } else {
                throw new PaymentDoesNotExistException();
            }
        } else {
            throw new CategoryDoesNotExistException();
        }
        return paymentRepo.getAllByUserIDAndCategoryID(userID, payment.getCategoryID());
    }
}
