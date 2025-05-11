package com.example.projectc1023i1.Dto.get_data.collection_maptruck;

import com.example.projectc1023i1.model.Collection;
import com.example.projectc1023i1.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCollectionMapStruck {
    private Integer id;
    private Product product;
}
