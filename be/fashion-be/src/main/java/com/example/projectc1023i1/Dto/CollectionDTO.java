package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    @JsonProperty("imageUrl")
    private MultipartFile imageUrl;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("startDate")
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("endDate")
    private LocalDateTime endDate;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("product")
    private List<Product> product;
}
