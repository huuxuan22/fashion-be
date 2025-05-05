package com.example.projectc1023i1.Dto.get_data.cart_mapstruck;

import com.example.projectc1023i1.model.Cart;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemMapper {
    private Long cartItemId;
    private ProductVariant productVariant;
    private Product product;
    private Integer quantity;
}
