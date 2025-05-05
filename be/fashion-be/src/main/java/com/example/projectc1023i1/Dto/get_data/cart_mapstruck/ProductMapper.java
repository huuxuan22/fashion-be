package com.example.projectc1023i1.Dto.get_data.cart_mapstruck;

import com.example.projectc1023i1.model.SubCategories;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMapper {
    private Integer productId;
    private String productName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String thumbnail;
    private Integer quality;
    private Integer sellPrice;
    private Integer price;
}
