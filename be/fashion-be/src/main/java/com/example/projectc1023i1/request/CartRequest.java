package com.example.projectc1023i1.request;

import com.example.projectc1023i1.Dto.ProductDetailDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {
    @JsonProperty("products")
    private List<ProductDetailDTO> products;
}
