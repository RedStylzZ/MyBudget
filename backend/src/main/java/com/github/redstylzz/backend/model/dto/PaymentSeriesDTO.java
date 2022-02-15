package com.github.redstylzz.backend.model.dto;

import com.github.redstylzz.backend.model.PaymentSeries;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSeriesDTO {

    @Nullable
    String seriesId;
    Instant startDate;
    Instant endDate;
    PaymentDTO payment;
    int scheduledDate;

    @Transient
    public static PaymentSeriesDTO mapSeriesToDTO(PaymentSeries series) {
        return PaymentSeriesDTO.builder()
                .seriesId(series.getSeriesId())
                .startDate(series.getStartDate())
                .endDate(series.getEndDate())
                .payment(series.getPayment())
                .scheduledDate(series.getScheduledDate())
                .build();
    }
}
