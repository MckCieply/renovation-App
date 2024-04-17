package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseEntity;
import com.mckcieply.renovationapp.enumerable.EnumContractorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contractors")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Contractor extends BaseEntity {

    @NotBlank(message = "First name is mandatory")
    @Size(min=3, max = 100, message = "First name must be between 3 and 100 characters long")
    private String firstName;           // For private person or contactPerson for company

    @NotBlank(message = "Last name is mandatory")
    @Size(min=3, max = 100, message = "Last name must be between 3 and 100 characters long")
    private String lastName;

    @Transient
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Size(min=3, max = 100, message = "Email must be between 3 and 100 characters long")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    @Size(min=8, max = 12, message = "Phone must be between 8 and 12 characters long")
    private String phone;

    @NotBlank(message = "Type is mandatory")
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
