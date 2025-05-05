package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountByQualitySize {
    private Size size;
    private Integer quality;
}
