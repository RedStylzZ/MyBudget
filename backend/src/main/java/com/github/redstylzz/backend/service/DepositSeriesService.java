package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.SeriesAlreadyExistException;
import com.github.redstylzz.backend.model.DepositSeries;
import com.github.redstylzz.backend.model.PaymentSeries;
import com.github.redstylzz.backend.model.dto.DepositSeriesDTO;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.repository.IDepositSeriesRepository;
import com.github.redstylzz.backend.repository.IPaymentSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositSeriesService {
    private static final Log LOG = LogFactory.getLog(DepositSeriesService.class);

    private final IDepositSeriesRepository repository;

    public List<DepositSeries> getSeries(String userId) {
        LOG.info("Fetching series");
        return repository.getAllByUserId(userId);
    }

    public List<DepositSeries> addSeries(String userId, DepositSeriesDTO dto) {
        LOG.info("Adding series");
        DepositSeries series = DepositSeries.mapDTOtoSeries(dto, userId);
        if (!repository.existsBySeriesId(series.getSeriesId())) {
            repository.save(series);
        } else {
            LOG.warn("Series already existent");
            throw new SeriesAlreadyExistException();
        }

        return repository.getAllByUserId(userId);
    }

    public List<DepositSeries> deleteSeries(String userId, String seriesId) {
        LOG.info("Deleting series");
        repository.deleteByUserIdAndSeriesId(userId, seriesId);
        return repository.getAllByUserId(userId);
    }

    public List<DepositSeries> changeSeries(String userId, DepositSeriesDTO dto) {
        if (repository.existsByUserIdAndSeriesId(userId, dto.getSeriesId())) {
            DepositSeries series = DepositSeries.mapDTOtoSeries(dto, userId);
            repository.save(series);
        }
        return repository.getAllByUserId(userId);
    }

}
