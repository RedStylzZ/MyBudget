package com.github.redstylzz.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPaymentInputDTO {
    String categoryID;
    String paymentID;
}
