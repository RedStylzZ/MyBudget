package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentDTO {

    String categoryID;
    String description;
    double amount;
    Date payDate;
}
