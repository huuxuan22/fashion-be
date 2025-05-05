package com.example.projectc1023i1.Dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// dday chinh la class dung de mua hang chua thong tin order va thogn tin cua nguoi mua
public class PurchaseDTO {
    private List<ProductDetailDTO> productDetail;
    private OrderDetailDTO orderDetail;
    private String token;
}
