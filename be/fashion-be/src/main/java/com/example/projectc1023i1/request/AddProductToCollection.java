package com.example.projectc1023i1.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductToCollection {
    private List<Integer> productIds;

    private Integer collectionId;

}
