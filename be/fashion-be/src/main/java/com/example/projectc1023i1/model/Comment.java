package com.example.projectc1023i1.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private String productId;
    private String sender;
    private String comment;
}
