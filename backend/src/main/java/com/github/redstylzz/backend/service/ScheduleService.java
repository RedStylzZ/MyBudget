package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.PaymentSeries;
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

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleService {
    private static final Log LOG = LogFactory.getLog(ScheduleService.class);
    private final IPaymentSeriesRepository repository;
    private final IPaymentRepository paymentRepository;

    @Scheduled(fixedDelay = 300000L)
    public void addPayment() {
        List<PaymentSeries> series = repository.getAllByScheduledDate(LocalDateTime.now().getDayOfMonth());
        if (!series.isEmpty()) {
            List<Payment> payments = series
                    .stream()
                    .map(s -> Payment.convertDTOtoPayment(s.getPayment(), s.getUserId(), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)))
                    .toList();

            paymentRepository.saveAll(payments);
        }
    }
}
