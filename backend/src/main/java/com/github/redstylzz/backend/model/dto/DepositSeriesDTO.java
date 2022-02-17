package com.github.redstylzz.backend.model.dto;

import com.github.redstylzz.backend.model.DepositSeries;
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
public class DepositSeriesDTO {

    @Nullable
    String seriesId;
    Instant startDate;
    Instant endDate;
    DepositDTO deposit;
    int scheduledDate;

    @Transient
    public static DepositSeriesDTO mapSeriesToDTO(DepositSeries series) {
        return DepositSeriesDTO.builder()
                .seriesId(series.getSeriesId())
                .startDate(series.getStartDate())
                .endDate(series.getEndDate())
                .deposit(series.getDeposit())
                .scheduledDate(series.getScheduledDate())
                .build();
    }
}
