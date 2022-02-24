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
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final Log LOG = LogFactory.getLog(CategoryService.class);

    private final IPaymentRepository paymentRepository;
    private final ICategoryRepository categoryRepository;

    private boolean categoryExistent(String userId, String categoryId) {
        return categoryRepository.existsByUserIdAndCategoryId(userId, categoryId);
    }

    private boolean paymentExists(String paymentId) {
        return paymentRepository.existsByPaymentId(paymentId);
    }

    public BigDecimal calculatePaymentSum(String userId, String categoryId) {
        List<Payment> payments = paymentRepository
                .getAllByUserIdAndCategoryIdAndPayDateAfterOrderByPayDateDesc(userId, categoryId, LocalDateTime.now().withDayOfMonth(1).minus(Duration.ofDays(1)).toInstant(ZoneOffset.UTC));
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Payment getPayment(String userId, String categoryId, String paymentId) {
        return paymentRepository
                .getByUserIdAndCategoryIdAndPaymentId(userId, categoryId, paymentId);
    }

    public List<Payment> getPayments(String userId, String categoryId) {
        return paymentRepository
                .getAllByUserIdAndCategoryIdOrderByPayDateDesc(userId, categoryId);
    }

    public List<Payment> getLastPayments(String userId) {
        return paymentRepository
                .getAllByUserIdAndPayDateAfterOrderByPayDateDesc(userId, Instant.now().minus(Duration.ofDays(7)));
    }

    public List<Payment> addPayment(String userId, Payment payment) throws CategoryDoesNotExistException {
        if (categoryExistent(userId, payment.getCategoryId())) {
            LOG.debug(payment.getPayDate());
            payment.setUserId(userId);
            payment.setSaveDate(Instant.now());
            paymentRepository.save(payment);
            calculatePaymentSum(userId, payment.getCategoryId());
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userId, payment.getCategoryId());
    }

    public List<Payment> deletePayment(String userId, String categoryId, String paymentId) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userId, categoryId)) {
            paymentRepository.deleteByPaymentId(paymentId);
            calculatePaymentSum(userId, categoryId);
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userId, categoryId);
    }

    public List<Payment> changePayment(String userId, Payment payment) throws PaymentDoesNotExistException, CategoryDoesNotExistException {
        if (categoryExistent(userId, payment.getCategoryId())) {
            if (paymentExists(payment.getPaymentId())) {
                payment.setUserId(userId);
                payment.setSaveDate(Instant.now());
                paymentRepository.save(payment);
                calculatePaymentSum(userId, payment.getCategoryId());
            } else {
                throw new PaymentDoesNotExistException();
            }
        } else {
            throw new CategoryDoesNotExistException();
        }
        return getPayments(userId, payment.getCategoryId());
    }
}
