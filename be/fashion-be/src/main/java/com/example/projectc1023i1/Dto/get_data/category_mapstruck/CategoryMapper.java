package com.example.projectc1023i1.Dto.get_data.category_mapstruck;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryMapper {
    private int categorieId;
    private String categoriesName;
    private String description;
    private String thumbnail;
    private List<SubCategoryMapper> categories;
}
