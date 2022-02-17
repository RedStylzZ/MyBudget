package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.DepositSeries;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.PaymentSeries;
import com.github.redstylzz.backend.repository.IDepositRepository;
import com.github.redstylzz.backend.repository.IDepositSeriesRepository;
import com.github.redstylzz.backend.repository.IPaymentRepository;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleService {
    private static final Log LOG = LogFactory.getLog(ScheduleService.class);
    private final IPaymentSeriesRepository paymentSeriesRepository;
    private final IPaymentRepository paymentRepository;
    private final IDepositSeriesRepository depositSeriesRepository;
    private final IDepositRepository depositRepository;

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.DAYS)
    public void addPayment() {
        LOG.info("Adding payments from series");
        List<PaymentSeries> series = paymentSeriesRepository.getAllByScheduledDate(LocalDateTime.now().getDayOfMonth());
        if (!series.isEmpty()) {
            List<Payment> payments = series
                    .stream()
                    .map(s -> Payment.convertDTOtoPayment(s.getPayment(), s.getUserId(), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)))
                    .toList();
            paymentRepository.saveAll(payments);
        }
    }

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.DAYS)
    public void addDeposit() {
        LOG.info("Adding deposit from series");
        List<DepositSeries> series = depositSeriesRepository.getAllByScheduledDate(LocalDateTime.now().getDayOfMonth());
        if (!series.isEmpty()) {
            List<Deposit> deposits = series
                    .stream()
                    .map(s -> Deposit.mapDTOtoDeposit(s.getDeposit(), s.getUserId(), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)))
                    .toList();
            depositRepository.saveAll(deposits);
        }
    }
}
