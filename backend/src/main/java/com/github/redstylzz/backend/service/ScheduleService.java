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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleService {
    private static final Log LOG = LogFactory.getLog(ScheduleService.class);
    private final IPaymentSeriesRepository repository;
    private final IPaymentRepository paymentRepository;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void helloWorld() {
        LOG.debug("Hello World");
    }

    @Scheduled(fixedDelay = 2500L)
    public void goodByWorld() {
        LOG.debug("Goodbye World");
    }

//    @Scheduled(fixedDelay = 5000L)
//    public void addPayment() {
//        List<PaymentSeries> series = repository.getAllByScheduledDate(LocalDateTime.now().getDayOfMonth());
//        LOG.debug("Series: " + series);
//        List<Payment> payments = series.stream()
//                .map(PaymentSeries::getPayment)
//                .map(lPayment -> {
//                    lPayment.setPaymentID(UUID.randomUUID().toString());
//                    lPayment.setPayDate(LocalDate.now());
//                    return lPayment;
//                })
//                .map(Payment::convertDTOtoPayment)
//                .toList();
//
//        LOG.debug("Payments: " + payments);
//        paymentRepository.saveAll(payments);
//    }
}
