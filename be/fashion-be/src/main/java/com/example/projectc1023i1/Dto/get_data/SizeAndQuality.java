package com.example.projectc1023i1.Dto.get_data;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * đây là class lấy ra xem những size của 1 sản phẩm coi thử nó còn hàng hay không
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SizeAndQuality {
    @JsonProperty(namespace = "size_id")
    private Integer sizeId;
    @JsonProperty(namespace = "name_size")
    private String nameSize;
    @JsonProperty(namespace = "stock")
    private Integer stock;
}
