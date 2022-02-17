package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("paymentSeries")
public class PaymentSeries {

    @Id
    String seriesId;

    String userId;
    Instant startDate;
    Instant endDate;
    PaymentDTO payment;
    int scheduledDate;

    @Transient
    public static PaymentSeries mapDTOtoSeries(PaymentSeriesDTO dto, String userId) {
        return PaymentSeries.builder()
                .userId(userId)
                .seriesId(dto.getSeriesId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .scheduledDate(dto.getScheduledDate())
                .payment(dto.getPayment())
                .build();
    }
}
