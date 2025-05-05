package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "commune")
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commune_id")
    private Long communeId;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
}
