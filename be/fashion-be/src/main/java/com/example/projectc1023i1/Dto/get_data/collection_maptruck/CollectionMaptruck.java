package com.example.projectc1023i1.Dto.get_data.collection_maptruck;


import com.example.projectc1023i1.model.ProductCollection;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionMaptruck {
    private Long collectionId;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private List<ProductCollectionMapStruck> productCollections;
}
