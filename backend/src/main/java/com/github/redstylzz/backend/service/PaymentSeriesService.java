package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.PaymentSeries;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentSeriesService {
    private static final Log LOG = LogFactory.getLog(PaymentSeriesService.class);

    private final IPaymentSeriesRepository repository;

    public List<PaymentSeries> getSeries(String userId) {
        LOG.info("Get series");
        return repository.getAllByUserId(userId);
    }

    public List<PaymentSeries> addSeries(String userId, PaymentSeriesDTO dto) {
        LOG.info("Add series");
        PaymentSeries series = PaymentSeries.mapDTOtoSeries(dto, userId);

        if (!repository.existsBySeriesId(series.getSeriesId())) {
            repository.save(series);
        }

        return getSeries(userId);
    }

}