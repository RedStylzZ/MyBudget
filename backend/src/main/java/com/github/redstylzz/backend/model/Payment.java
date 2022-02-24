package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.model.dto.RequestPaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("payment")
public class Payment {

    @Id
    String paymentId;

    String userId;
    String categoryId;
    String description;
    BigDecimal amount;
    Instant saveDate;
    Instant payDate;

    public static Payment convertDTOtoPayment(PaymentDTO dto) {
        return Payment.builder()
                .paymentId(dto.getPaymentId())
                .categoryId(dto.getCategoryId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .payDate(dto.getPayDate().toInstant(ZoneOffset.UTC))
                .build();
    }

    public static Payment convertDTOtoPayment(RequestPaymentDTO dto) {
        return Payment.builder()
                .categoryId(dto.getCategoryId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .payDate(dto.getPayDate().toInstant(ZoneOffset.UTC))
                .build();
    }

    public static Payment convertDTOtoPayment(PaymentDTO dto, String userId) {
        return Payment.builder()
                .userId(userId)
                .categoryId(dto.getCategoryId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .payDate(dto.getPayDate().toInstant(ZoneOffset.UTC))
                .build();
    }

    public static Payment convertDTOtoPayment(PaymentDTO dto, String userId, Instant payDate) {
        return Payment.builder()
                .userId(userId)
                .categoryId(dto.getCategoryId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .payDate(payDate)
                .build();
    }

    public static PaymentDTO convertPaymentToDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.paymentId)
                .categoryId(payment.categoryId)
                .description(payment.description)
                .amount(payment.amount)
                .payDate(LocalDateTime.from(payment.payDate.atZone(ZoneId.of("GMT+1"))))
                .build();
    }
}
