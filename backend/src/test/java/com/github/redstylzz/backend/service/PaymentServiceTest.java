package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.CategoryDoesNotExistException;
import com.github.redstylzz.backend.exception.PaymentDoesNotExistException;
import com.github.redstylzz.backend.model.Category;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.TestDataProvider;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.repository.ICategoryRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private final IPaymentRepository paymentRepo = mock(IPaymentRepository.class);
    private final ICategoryRepository categoryRepository = mock(ICategoryRepository.class);
    private final PaymentService underTest = new PaymentService(paymentRepo, categoryRepository);
    private static final String UUID_STRING = "06f1eb01-cdaf-46e4-a3c8-eff2e4b300dd";
    private static final UUID uuid = UUID.fromString(UUID_STRING);
    private static MockedStatic<UUID> uuidMock;


    @BeforeAll
    static void init() {
        uuidMock = mockStatic(UUID.class);
        uuidMock.when(UUID::randomUUID).thenReturn(uuid);
    }

    @AfterAll
    static void end() {
        uuidMock.close();
    }

    @Test
    void shouldReturnList_OnGet() {
        Category category = TestDataProvider.testCategory();
        Payment actualPayment = TestDataProvider.testPayment();
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(actualPayment));

        List<PaymentDTO> payments = underTest.getPayments(category.getUserID(), category.getCategoryID());

        verify(paymentRepo).getAllByUserIDAndCategoryID(anyString(), anyString());
        assertEquals(List.of(Payment.convertPaymentToDTO(actualPayment)), payments);
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
        Payment actualPayment = TestDataProvider.testPayment();
        actualPayment.setPaymentID(UUID_STRING);
        String userID = actualPayment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(actualPayment));

        assertEquals(List.of(Payment.convertPaymentToDTO(actualPayment)), underTest.addPayment(userID, actualPayment));
        verify(paymentRepo).save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist_OnDelete() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userID = actualPayment.getUserID();
        String categoryID = actualPayment.getCategoryID();
        String paymentID = actualPayment.getPaymentID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(false);

        assertThrows(CategoryDoesNotExistException.class, () -> underTest.deletePayment(userID, categoryID, paymentID));
    }

    @Test
    void shouldReturnListAfterDeletingPayment_OnDelete() {
        Payment actualPayment = TestDataProvider.testPayment();
        String userID = actualPayment.getUserID();
        String categoryID = actualPayment.getCategoryID();
        String paymentID = actualPayment.getPaymentID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(actualPayment));

        assertEquals(List.of(Payment.convertPaymentToDTO(actualPayment)), underTest.deletePayment(userID, categoryID, paymentID));
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
        Payment actualPayment = TestDataProvider.testPayment();
        String userID = actualPayment.getUserID();
        when(categoryRepository.existsByUserIDAndCategoryID(anyString(), anyString())).thenReturn(true);
        when(paymentRepo.getAllByUserIDAndCategoryID(anyString(), anyString())).thenReturn(List.of(actualPayment));
        when(paymentRepo.existsByPaymentID(anyString())).thenReturn(true);

        assertEquals(List.of(Payment.convertPaymentToDTO(actualPayment)), underTest.changePayment(userID, actualPayment));
        verify(paymentRepo).save(any(Payment.class));
    }


}