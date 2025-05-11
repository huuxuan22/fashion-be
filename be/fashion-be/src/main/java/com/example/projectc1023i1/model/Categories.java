package com.example.projectc1023i1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class    Categories {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private int categorieId;

    @Column(name = "categories_name")
    private String categoriesName;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SubCategories> subCategories;
}
