package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_categories")
public class SubCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_cate_id")
    private Long subCategoryId;

    @Column(name = "sub_cate_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;
}
