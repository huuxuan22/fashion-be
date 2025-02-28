package com.example.projectc1023i1.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDelete {
    private Integer productId;
    private List<Integer> imageUrls;
}
