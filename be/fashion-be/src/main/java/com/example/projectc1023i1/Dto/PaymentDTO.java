package com.example.projectc1023i1.Dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO implements Serializable {
    private String status;
    private String message;
    private String URL;
}
