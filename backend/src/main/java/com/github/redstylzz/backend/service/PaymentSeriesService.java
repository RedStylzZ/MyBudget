package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.SeriesAlreadyExistException;
import com.github.redstylzz.backend.exception.SeriesDoesNotExistException;
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
    private static final String SERIES_DOES_NOT_EXIST = "Series does not exist";
    private static final String SERIES_ALREADY_EXIST = "Series already exist";
    private final IPaymentSeriesRepository repository;

    public List<PaymentSeries> getSeries(String userId) {
        LOG.info("Get series");
        return repository.getAllByUserId(userId);
    }

    public List<PaymentSeries> addSeries(String userId, PaymentSeriesDTO dto) {
        LOG.info("Adding series");
        PaymentSeries series = PaymentSeries.mapDTOtoSeries(dto, userId);
        if (series.getSeriesId() == null || !repository.existsBySeriesId(series.getSeriesId())) {
            repository.save(series);
        } else {
            LOG.warn(SERIES_ALREADY_EXIST);
            throw new SeriesAlreadyExistException();
        }
        LOG.info("Successfully added series");
        return getSeries(userId);
    }

    public List<PaymentSeries> deleteSeries(String userId, String seriesId) {
        LOG.info("Deleting series");
        repository.deleteByUserIdAndSeriesId(userId, seriesId);
        return getSeries(userId);
    }

    public List<PaymentSeries> changeSeries(String userId, PaymentSeriesDTO dto) {
        LOG.info("Changing series");
        if (repository.existsByUserIdAndSeriesId(userId, dto.getSeriesId())) {
            PaymentSeries series = PaymentSeries.mapDTOtoSeries(dto, userId);
            repository.save(series);
        } else {
            LOG.warn(SERIES_DOES_NOT_EXIST);
            throw new SeriesDoesNotExistException();
        }
        LOG.info("Successfully changed series");
        return getSeries(userId);
    }

}
