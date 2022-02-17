package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    private final IPaymentRepository paymentRepository;
    private final ICategoryRepository categoryRepository;

    private boolean categoryExistent(String userID, String categoryID) {
        return categoryRepository.existsByUserIDAndCategoryID(userID, categoryID);
    }

    private boolean paymentExists(String paymentID) {
        return paymentRepository.existsByPaymentID(paymentID);
    }

    public BigDecimal calculatePaymentSum(String userID, String categoryID) {
        List<Payment> payments = paymentRepository
                .getAllByUserIDAndCategoryIDAndPayDateAfterOrderByPayDateDesc(userID, categoryID, LocalDateTime.now().withDayOfMonth(1).minus(Duration.ofDays(1)));
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Payment getPayment(String userID, String categoryID, String paymentID) {
        return paymentRepository
                .getByUserIDAndCategoryIDAndPaymentID(userID, categoryID, paymentID);
    }

    public List<Payment> getPayments(String userID, String categoryID) {
        return paymentRepository
                .getAllByUserIDAndCategoryIDOrderByPayDateDesc(userID, categoryID);
    }

    public List<Payment> getLastPayments(String userID) {
        return paymentRepository
                .getAllByUserIDAndPayDateAfterOrderByPayDateDesc(userID, Instant.now().minus(Duration.ofDays(7)));
    }

    public List<Payment> addPayment(String userID, Payment payment) throws CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            LOG.debug(payment.getPayDate());
            payment.setUserID(userID);
            payment.setSaveDate(Instant.now());
            paymentRepository.save(payment);
            calculatePaymentSum(userID, payment.getCategoryID());
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userID, payment.getCategoryID());
    }

    public List<Payment> deletePayment(String userID, String categoryID, String paymentID) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, categoryID)) {
            paymentRepository.deleteByPaymentID(paymentID);
            calculatePaymentSum(userID, categoryID);
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userID, categoryID);
    }

    public List<Payment> changePayment(String userID, Payment payment) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            if (paymentExists(payment.getPaymentID())) {
                payment.setUserID(userID);
                payment.setSaveDate(Instant.now());
                paymentRepository.save(payment);
                calculatePaymentSum(userID, payment.getCategoryID());
            } else {
                throw new PaymentDoesNotExistException();
            }
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userID, payment.getCategoryID());
    }
}
