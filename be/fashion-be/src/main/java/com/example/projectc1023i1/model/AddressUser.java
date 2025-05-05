package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AddressUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_user_id")
    private Integer addressUserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province")
    private Province province;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id")
    private Commune commune;
    @Column(name = "home_address")
    private String homeAddress;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private String phone;

}
