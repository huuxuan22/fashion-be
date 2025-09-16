package com.example.projectc1023i1.Dto;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDTO {
    @Column(name = "sub_cate_name")
    private String subCategoryName;

    private Integer categoryId;
}
