package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class DealDTO implements Serializable {
    @JsonProperty("product")
    private List<Integer> product;

    @JsonProperty("discountPercent")
    private Double discountPercent;

    @JsonProperty("dealPrice")
    private Double dealPrice;

    @JsonProperty("startTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @JsonProperty("endTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    @JsonProperty("imageFile")
    private MultipartFile imageFile;
}
