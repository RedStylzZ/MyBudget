package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    String paymentId;
    String categoryId;
    String description;
    BigDecimal amount;
    LocalDateTime payDate;
}
