package com.dosmartie.entity;

import com.dosmartie.helper.CredentialProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant_info")
public class MerchantInfo implements CredentialProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String role;
    private boolean isVerified;
    private boolean isAdmin;
    @ManyToOne
    @JoinColumn(name = "company_address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
