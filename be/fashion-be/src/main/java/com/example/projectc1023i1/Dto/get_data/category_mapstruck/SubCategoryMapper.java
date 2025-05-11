package com.example.projectc1023i1.Dto.get_data.category_mapstruck;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryMapper {
    private Long subCategoryId;
    private String subCategoryName;
}
