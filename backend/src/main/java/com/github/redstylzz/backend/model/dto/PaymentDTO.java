package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDTO {

    String paymentID;
    String categoryID;
    String description;
}
