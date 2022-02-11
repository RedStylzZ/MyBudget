package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

}
