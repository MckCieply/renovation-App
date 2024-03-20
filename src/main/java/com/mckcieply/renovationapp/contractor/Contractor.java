package com.mckcieply.renovationapp.contractor;

import com.mckcieply.renovationapp.enumerable.EnumContractorType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contractors")
@Data
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private EnumContractorType type;

    private String companyName;

    private String nip;

    private String regon;

    private String address;

    private String city;

    private String postalCode;

    private String country;

    private String bankAccount;

    private String description;


}
