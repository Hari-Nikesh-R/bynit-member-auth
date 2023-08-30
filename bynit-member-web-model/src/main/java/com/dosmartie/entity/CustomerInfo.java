package com.dosmartie.entity;

import com.dosmartie.helper.CredentialProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_info")
public class CustomerInfo implements CredentialProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String role;
    //private ArrayList<Address> homeAddress;
}
