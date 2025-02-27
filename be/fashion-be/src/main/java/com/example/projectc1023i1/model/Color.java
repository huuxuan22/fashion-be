package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Integer colorId;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "color_code")
    private String colorCode;
}
