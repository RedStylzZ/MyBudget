package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Document("payment")
public class Payment {

    @Id
    String paymentID;

    String userID;
    String categoryID;
    String description;
    double amount;
    Date saveDate;
    Date payDate;

    public static Payment convertDTOtoPayment(PaymentDTO dto) {
        return Payment.builder()
                .paymentID(dto.getPaymentID())
                .categoryID(dto.getCategoryID())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .payDate(dto.getPayDate())
                .build();
    }
}
