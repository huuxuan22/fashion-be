package com.example.projectc1023i1.Dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddQualityProduct {
    private Integer sizeId;
    private Integer colorId;
    private Integer quantity;
}
