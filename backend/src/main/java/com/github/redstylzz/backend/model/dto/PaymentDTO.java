package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    String paymentID;
    String categoryID;
    String description;
    BigDecimal amount;
    Date payDate;
}
