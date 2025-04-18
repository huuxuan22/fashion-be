package com.example.projectc1023i1.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private String productId;
    private String sender;
    private String comment;
    private String unique;
    private Integer rating;
}
