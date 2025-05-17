package com.example.projectc1023i1.Dto.get_data.order_maptruck;

import com.example.projectc1023i1.model.Order;
import com.example.projectc1023i1.model.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailMaptruck {
    private Integer orderDetailsId;
    private Integer quality;
    private Double price;
    private ProductVariant productVariant;
}
