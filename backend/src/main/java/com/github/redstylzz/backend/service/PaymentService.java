package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        List<Payment> payments = paymentRepository.getAllByUserIDAndCategoryIDAndPayDateAfter(userID, categoryID, LocalDateTime.now().withDayOfMonth(1));
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PaymentDTO> getPaymentAsDTO(String userID, String categoryID) {
        return paymentRepository
                .getAllByUserIDAndCategoryID(userID, categoryID)
                .stream()
                .map(Payment::convertPaymentToDTO)
                .toList();
    }

    public PaymentDTO getPayment(String userID, String categoryID, String paymentID) {
        return Payment.convertPaymentToDTO(paymentRepository
                .getByUserIDAndCategoryIDAndPaymentID(userID, categoryID, paymentID));
    }

    public List<PaymentDTO> getPayments(String userID, String categoryID) {
        return getPaymentAsDTO(userID, categoryID);
    }

    public List<PaymentDTO> getLastPayments(String userID) {
        return paymentRepository
                .getAllByUserIDAndPayDateAfter(userID, LocalDateTime.now().minus(Duration.ofDays(7)))
                .stream().map(Payment::convertPaymentToDTO)
                .toList();
    }

    public List<PaymentDTO> addPayment(String userID, Payment payment) throws CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            payment.setPaymentID(UUID.randomUUID().toString());
            payment.setUserID(userID);
            payment.setSaveDate(LocalDateTime.now());
            paymentRepository.save(payment);
            calculatePaymentSum(userID, payment.getCategoryID());
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPaymentAsDTO(userID, payment.getCategoryID());
    }

    public List<PaymentDTO> deletePayment(String userID, String categoryID, String paymentID) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, categoryID)) {
            paymentRepository.deleteByPaymentID(paymentID);
            calculatePaymentSum(userID, categoryID);
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPaymentAsDTO(userID, categoryID);
    }

    public List<PaymentDTO> changePayment(String userID, Payment payment) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userID, payment.getCategoryID())) {
            if (paymentExists(payment.getPaymentID())) {
                payment.setUserID(userID);
                payment.setSaveDate(LocalDateTime.now());
                paymentRepository.save(payment);
                calculatePaymentSum(userID, payment.getCategoryID());
            } else {
                throw new PaymentDoesNotExistException();
            }
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPaymentAsDTO(userID, payment.getCategoryID());
    }
}
