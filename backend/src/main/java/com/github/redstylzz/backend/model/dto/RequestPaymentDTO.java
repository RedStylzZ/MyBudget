package com.github.redstylzz.backend.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPaymentDTO {

    @NonNull String categoryID;
    @NonNull String description;
    @NonNull BigDecimal amount;
    @NonNull ZonedDateTime payDate;
}
