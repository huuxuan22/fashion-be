package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.model.Commune;
import com.example.projectc1023i1.model.District;
import com.example.projectc1023i1.model.Province;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// class chua thong tin cua nguoi dung khi mua san pham
public class OrderDetailDTO {
    private Commune commune;
    private District district;
    private Province province;
    private String numberPhone;
    private String status;
    private String street;
    private String paymentType;
    private String note;
    private Double totalPrice;
}
