package com.github.redstylzz.backend.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPaymentDTO {

    @NonNull String categoryID;
    @NonNull String description;
    @NonNull BigDecimal amount;
    @NonNull Date payDate;
}
