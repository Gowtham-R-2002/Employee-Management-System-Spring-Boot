package com.i2i.ems.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;

/**
 * <p>
 * Address is the exact residing location of a person
 * Represents a employee's address and contains details of the address.
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "door_number")
    private String doorNumber;

    private String locality;
    private String city;
}