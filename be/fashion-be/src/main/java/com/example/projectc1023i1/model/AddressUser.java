package com.example.projectc1023i1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_user_id")
    private Integer addressUserId;


    @ManyToOne
    @JoinColumn(name = "province")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;


    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;


    @Column(name = "home_address")
    private String homeAddress;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String phone;

}
