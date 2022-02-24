package com.github.redstylzz.backend.repository;

import com.github.redstylzz.backend.model.PaymentSeries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentSeriesRepository extends MongoRepository<PaymentSeries, String> {

    List<PaymentSeries> getAllByUserId(String userId);

    List<PaymentSeries> getAllByScheduledDate(int scheduledDate);

    boolean existsByUserIdAndSeriesId(String userId, String seriesId);

    void deleteByUserIdAndSeriesId(String userId, String seriesId);

    void deleteAllByUserIdAndPayment_CategoryId(String userId, String categoryId);

    boolean existsBySeriesId(String seriesId);

}
