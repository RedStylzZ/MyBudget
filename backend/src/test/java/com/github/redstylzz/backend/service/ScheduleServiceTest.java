package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.repository.IDepositRepository;
import com.github.redstylzz.backend.repository.IDepositSeriesRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.redstylzz.backend.model.TestDataProvider.testDepositSeries;
import static com.github.redstylzz.backend.model.TestDataProvider.testPaymentSeries;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private final IPaymentSeriesRepository paymentSeriesRepository = mock(IPaymentSeriesRepository.class);
    private final IPaymentRepository paymentRepository = mock(IPaymentRepository.class);
    private final IDepositSeriesRepository depositSeriesRepository = mock(IDepositSeriesRepository.class);
    private final IDepositRepository depositRepository = mock(IDepositRepository.class);
    private final ScheduleService underTest = new ScheduleService(paymentSeriesRepository, paymentRepository, depositSeriesRepository, depositRepository);

    @Test
    void shouldSavePaymentsIfSeriesExistent() {
        when(paymentSeriesRepository.getAllByScheduledDate(anyInt())).thenReturn(List.of(testPaymentSeries()));

        underTest.addPayment();

        verify(paymentRepository).saveAll(anyList());
    }

    @Test
    void shouldNotSavePaymentsIfSeriesDoesNotExist() {
        when(paymentSeriesRepository.getAllByScheduledDate(anyInt())).thenReturn(List.of());

        underTest.addPayment();

        verifyNoInteractions(paymentRepository);
    }

    @Test
    void shouldSaveDepositIfSeriesExistent() {
        when(depositSeriesRepository.getAllByScheduledDate(anyInt())).thenReturn(List.of(testDepositSeries()));

        underTest.addDeposit();

        verify(depositRepository).saveAll(anyList());
    }

    @Test
    void shouldNotSaveDepositIfSeriesDoesNotExist() {
        when(depositSeriesRepository.getAllByScheduledDate(anyInt())).thenReturn(List.of());

        underTest.addDeposit();

        verifyNoInteractions(depositRepository);
    }

}