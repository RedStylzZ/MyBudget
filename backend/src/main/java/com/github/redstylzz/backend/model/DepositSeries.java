package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.DepositDTO;
import com.github.redstylzz.backend.model.dto.DepositSeriesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositSeries {

    @Id
    String seriesId;
    String userId;
    Instant startDate;
    Instant endDate;
    int scheduledDate;
    DepositDTO deposit;

    @Transient
    public static DepositSeries mapDTOtoSeries(DepositSeriesDTO dto, String userId) {
        return DepositSeries.builder()
                .userId(userId)
                .seriesId(dto.getSeriesId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .scheduledDate(dto.getScheduledDate())
                .deposit(dto.getDeposit())
                .build();
    }
}
