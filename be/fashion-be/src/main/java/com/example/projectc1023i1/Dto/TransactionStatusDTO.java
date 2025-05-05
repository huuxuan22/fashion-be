package com.example.projectc1023i1.Dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusDTO implements Serializable {
    private String status;
    private String message;
    private String data;
}
