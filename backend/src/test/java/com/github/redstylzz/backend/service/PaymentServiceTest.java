package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private final IPaymentRepository paymentRepo = mock(IPaymentRepository.class);
    private final ICategoryRepository categoryRepository = mock(ICategoryRepository.class);
    private final PaymentService underTest = new PaymentService(paymentRepo, categoryRepository);


    @Test
    void shouldReturnList_OnGet() {
        underTest.getPayments(TestDataProvider.testUser().getId(), TestDataProvider.testCategory().getCategoryID());

        verify(paymentRepo).getAllByUserIDAndCategoryID(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnAdd(){
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.addPayment(userID, payment));
    }

    @Test
    void shouldReturnListAfterAddingPayment_OnAdd() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(payment));

        assertEquals(List.of(payment), underTest.addPayment(userID, payment));
        verify(paymentRepo).save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnDelete() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.deletePayment(userID, payment));
    }

    @Test
    void shouldReturnListAfterDeletingPayment_OnDelete() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(payment));

        assertEquals(List.of(payment), underTest.deletePayment(userID, payment));
        verify(paymentRepo).deleteByPaymentID(anyString());
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnChange() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.changePayment(userID, payment));
    }

    @Test
    void shouldThrowExceptionIfPaymentDoesNotExist_OnChange() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.existsByPaymentID(anyString())).thenReturn(false);

        assertThrows(PaymentDoesNotExistException.class, () -> underTest.changePayment(userID, payment));
    }

    @Test
    void shouldReturnListAfterSuccessfulChangingPayment_OnChange() {
        Payment payment = TestDataProvider.testPayment();
        String userID = payment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(payment));
        when(paymentRepo.existsByPaymentID(anyString())).thenReturn(true);

        assertEquals(List.of(payment), underTest.changePayment(userID, payment));
        verify(paymentRepo).save(any(Payment.class));
    }


}