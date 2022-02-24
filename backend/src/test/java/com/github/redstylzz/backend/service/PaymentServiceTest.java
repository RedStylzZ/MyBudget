package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.github.redstylzz.backend.model.TestDataProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private final IPaymentRepository paymentRepo = mock(IPaymentRepository.class);
    private final ICategoryRepository categoryRepository = mock(ICategoryRepository.class);
    private final PaymentService underTest = new PaymentService(paymentRepo, categoryRepository);

    @Test
    void shouldReturnList_OnGet() {
        Category category = TestDataProvider.testCategory();
        Payment actualPayment = TestDataProvider.testPayment();
        when(paymentRepo.getAllByUserIdAndCategoryIdOrderByPayDateDesc(anyString(), anyString())).thenReturn(List.of(actualPayment));

        List<Payment> payments = underTest.getPayments(category.getUserId(), category.getCategoryId());

        verify(paymentRepo).getAllByUserIdAndCategoryIdOrderByPayDateDesc(anyString(), anyString());
        assertEquals(List.of(actualPayment), payments);
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnAdd() {
        Payment payment = TestDataProvider.testPayment();
        String userId = payment.getUserId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.addPayment(userId, payment));
    }

    @Test
    void shouldReturnListAfterAddingPayment_OnAdd() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userId = actualPayment.getUserId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIdAndCategoryIdOrderByPayDateDesc(anyString(), anyString())).thenReturn(List.of(actualPayment));

        assertEquals(List.of(actualPayment), underTest.addPayment(userId, actualPayment));
        verify(paymentRepo).save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnDelete() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userId = actualPayment.getUserId();
        String categoryId = actualPayment.getCategoryId();
        String paymentId = actualPayment.getPaymentId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.deletePayment(userId, categoryId, paymentId));
    }

    @Test
    void shouldReturnListAfterDeletingPayment_OnDelete() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userId = actualPayment.getUserId();
        String categoryId = actualPayment.getCategoryId();
        String paymentId = actualPayment.getPaymentId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIdAndCategoryIdOrderByPayDateDesc(anyString(), anyString())).thenReturn(List.of(actualPayment));

        assertEquals(List.of(actualPayment), underTest.deletePayment(userId, categoryId, paymentId));
        verify(paymentRepo).deleteByPaymentId(anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnChange() {
        Payment payment = TestDataProvider.testPayment();
        String userId = payment.getUserId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.changePayment(userId, payment));
    }

    @Test
    void shouldThrowExceptionIfPaymentDoesNotExist_OnChange() {
        Payment payment = TestDataProvider.testPayment();
        String userId = payment.getUserId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.existsByPaymentId(anyString())).thenReturn(false);

        assertThrows(PaymentDoesNotExistException.class, () -> underTest.changePayment(userId, payment));
    }

    @Test
    void shouldReturnListAfterSuccessfulChangingPayment_OnChange() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userId = actualPayment.getUserId();
        when(categoryRepository.existsByUserIdAndCategoryId(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIdAndCategoryIdOrderByPayDateDesc(anyString(), anyString())).thenReturn(List.of(actualPayment));
        when(paymentRepo.existsByPaymentId(anyString())).thenReturn(true);

        assertEquals(List.of(actualPayment), underTest.changePayment(userId, actualPayment));
        verify(paymentRepo).save(any(Payment.class));
    }

    @Test
    void shouldSpecificPaymentFromUserAndDepositId() {
        Payment actualPayment = testPayment();
        String depositId = actualPayment.getPaymentId();
        String userId = testUser().getId();
        String categoryId = testCategory().getCategoryId();
        when(paymentRepo.getByUserIdAndCategoryIdAndPaymentId(anyString(), anyString(), anyString())).thenReturn(actualPayment);

        Payment deposit = underTest.getPayment(userId, depositId, categoryId);

        assertEquals(actualPayment, deposit);
    }

    @Test
    void shouldReturnAllDepositsOfCurrentMonthFromUser() {
        String userId = testUser().getId();
        List<Payment> actualPayments = List.of(testPayment());
        when(paymentRepo.getAllByUserIdAndPayDateAfterOrderByPayDateDesc(anyString(), any(Instant.class))).thenReturn(actualPayments);

        List<Payment> deposits = underTest.getLastPayments(userId);

        assertEquals(actualPayments, deposits);
    }

}
