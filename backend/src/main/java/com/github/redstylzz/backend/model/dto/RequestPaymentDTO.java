package com.github.redstylzz.backend.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPaymentDTO {

    @NonNull String categoryID;
    @NonNull String description;
    @NonNull BigDecimal amount;
    @NonNull Instant payDate;
}
