package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.model.Commune;
import com.example.projectc1023i1.model.District;
import com.example.projectc1023i1.model.Province;
import com.example.projectc1023i1.model.Users;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUserDTO {
    private Province province;
    private District district;
    private Commune commune;
    private String homeAddress;
    private String phone;
}
