package com.github.redstylzz.backend.service;

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
    void shouldReturnListOfPayments() {
        underTest.getPayments(TestDataProvider.testUser().getId(), TestDataProvider.testCategory().getCategoryID());

        verify(paymentRepo).getAllByUserIDAndCategoryID(anyString(), anyString());
    }




}