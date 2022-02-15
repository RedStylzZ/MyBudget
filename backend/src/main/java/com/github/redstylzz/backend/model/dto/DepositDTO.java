package com.github.redstylzz.backend.model.dto;

import com.mongodb.lang.Nullable;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositDTO {

    @Nullable
    String depositId;
    String description;
    BigDecimal amount;
    Instant depositDate;

}
