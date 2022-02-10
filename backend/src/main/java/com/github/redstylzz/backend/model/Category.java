package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("category")
public class Category {

    @Id
    String categoryID;

    String userID;
    String categoryName;
    BigDecimal paymentSum;

}
