package com.github.redstylzz.backend.model.dto;

import com.github.redstylzz.backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    String categoryName;
    String categoryID;

    @Transient
    public static CategoryDTO mapCategoryToDTO(Category category) {
        return CategoryDTO.builder()
                .categoryID(category.getCategoryID())
                .categoryName(category.getCategoryName())
                .build();
    }
}
