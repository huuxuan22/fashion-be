package com.example.projectc1023i1.Dto.get_data.cart_mapstruck;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Size;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantMapper {
    private Integer productVariantId;
    private String sku;
    private Double price;
    private Integer stock;
    private ProductMapper product;
    private Color color;
    private Size size;
}
