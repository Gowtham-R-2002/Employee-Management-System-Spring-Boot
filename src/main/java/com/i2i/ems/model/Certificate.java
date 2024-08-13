package com.i2i.ems.model;

import jakarta.persistence.Entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

/** 
 * <p>
 * Certificate is a reward or proof for certain achievement given for a person.
 * Represents a certificate given to an employee that contains name of the certificate 
 * and the collection of related employees in it 
 * </p>
 *
 * @author  Gowtham R
 * @version 1.0
 */

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue
    @Column(name = "certificate_id")
    private Long id;

    @Column(name = "certificate_name", unique = true)
    private String name;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ManyToMany(mappedBy = "certificates")
    private Set<Employee> employees;

}