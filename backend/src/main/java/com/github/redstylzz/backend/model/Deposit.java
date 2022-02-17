package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.DepositCreationDTO;
import com.github.redstylzz.backend.model.dto.DepositDTO;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("deposit")
public class Deposit {
    @Id
    String depositId;

    String userId;
    String description;
    BigDecimal amount;
    Instant saveDate;
    Instant depositDate;


    public static DepositDTO mapDepositToDTO(Deposit deposit) {
        return DepositDTO.builder()
                .depositId(deposit.depositId)
                .description(deposit.description)
                .amount(deposit.amount)
                .depositDate(deposit.depositDate)
                .build();
    }

    public static Deposit mapDTOtoDeposit(DepositDTO dto) {
        return Deposit.builder()
                .depositId(dto.getDepositId())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .depositDate(dto.getDepositDate())
                .build();
    }

    public static Deposit mapDTOtoDeposit(DepositCreationDTO dto) {
        return Deposit.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .depositDate(dto.getDepositDate())
                .build();
    }

    public static Deposit mapDTOtoDeposit(DepositDTO dto, String userId, Instant depositDate) {
        return Deposit.builder()
                .userId(userId)
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .depositDate(depositDate)
                .build();
    }
}
