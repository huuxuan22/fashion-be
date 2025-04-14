package com.example.projectc1023i1.Dto.get_data;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserSimpleDTO {
    private Integer userId;
    private String imgUrl;
    private String numberphone;
    private String fullName;
}
