package com.dosmartie.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private Integer pinCode;
    private String landmark;
    private String streetName;
}
