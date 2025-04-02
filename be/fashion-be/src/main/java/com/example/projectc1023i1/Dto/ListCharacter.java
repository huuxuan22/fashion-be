package com.example.projectc1023i1.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListCharacter {

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer quality;

    @NotNull(message = "Mã màu không được để trống")
    @Min(value = 1, message = "Mã màu phải là số dương")
    private Integer colorId;

    @NotNull(message = "Mã kích thước không được để trống")
    @Min(value = 1, message = "Mã kích thước phải là số dương")
    private Integer sizeId;
}