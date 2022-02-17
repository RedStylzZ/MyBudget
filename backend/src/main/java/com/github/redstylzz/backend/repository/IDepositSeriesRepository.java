package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.DepositSeries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepositSeriesRepository extends MongoRepository<DepositSeries, String> {

    List<DepositSeries> getAllByUserId(String userId);

    List<DepositSeries> getAllByScheduledDate(int scheduledDate);

    boolean existsByUserIdAndSeriesId(String userId, String seriesId);

    void deleteByUserIdAndSeriesId(String userId, String seriesId);

    boolean existsBySeriesId(String seriesId);

}
