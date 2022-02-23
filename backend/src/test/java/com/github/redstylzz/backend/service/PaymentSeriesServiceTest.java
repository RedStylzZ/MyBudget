package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.SeriesAlreadyExistException;
import com.github.redstylzz.backend.model.PaymentSeries;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.redstylzz.backend.model.TestDataProvider.*;
import static com.github.redstylzz.backend.model.TestDataProvider.testPaymentSeriesDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class PaymentSeriesServiceTest {
    
    private final IPaymentSeriesRepository paymentSeriesRepository = mock(IPaymentSeriesRepository.class);
    private final PaymentSeriesService underTest = new PaymentSeriesService(paymentSeriesRepository);
    private final MongoUser user = testUser();

    @Test
    void shouldReturnAllSeriesForUser() {
        List<PaymentSeries> actualSeries = List.of(testPaymentSeries());
        when(paymentSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<PaymentSeries> series = underTest.getSeries(testUser().getId());

        assertEquals(actualSeries, series);
    }

    @Test
    void shouldSaveAndReturnPaymentSeries() {
        List<PaymentSeries> actualSeries = List.of(testPaymentSeries());
        when(paymentSeriesRepository.existsBySeriesId(anyString())).thenReturn(false);
        when(paymentSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<PaymentSeries> series = underTest.addSeries(user.getId(), testPaymentSeriesDTO());

        verify(paymentSeriesRepository).save(any(PaymentSeries.class));
        assertEquals(actualSeries, series);
    }

    @Test
    void shouldThrowExceptionIfSeriesDoesNotExist() {
        String userId = user.getId();
        PaymentSeriesDTO dto = testPaymentSeriesDTO();
        when(paymentSeriesRepository.existsBySeriesId(anyString())).thenReturn(true);

        assertThrows(SeriesAlreadyExistException.class, () -> underTest.addSeries(userId, dto));
        verify(paymentSeriesRepository).existsBySeriesId(anyString());
        verifyNoMoreInteractions(paymentSeriesRepository);
    }

    @Test
    void shouldDeleteAndReturnPaymentSeriesForUser() {
        String userId = user.getId();
        List<PaymentSeries> actualSeries = List.of(testPaymentSeries());
        String seriesId = testPaymentSeriesDTO().getSeriesId();
        when(paymentSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<PaymentSeries> series = underTest.deleteSeries(userId, seriesId);

        assertEquals(actualSeries, series);
        verify(paymentSeriesRepository).deleteByUserIdAndSeriesId(anyString(), anyString());
    }

    @Test
    void shouldChangeSeries() {
        String userId = user.getId();
        PaymentSeriesDTO dto = testPaymentSeriesDTO();
        when(paymentSeriesRepository.existsByUserIdAndSeriesId(anyString(), anyString())).thenReturn(true);

        underTest.changeSeries(userId, dto);

        verify(paymentSeriesRepository).save(any(PaymentSeries.class));
        verify(paymentSeriesRepository).getAllByUserId(anyString());
    }
}