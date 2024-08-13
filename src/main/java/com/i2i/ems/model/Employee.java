package com.i2i.ems.model;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

/**
 * <p>
 * Represents an employee in an organisation
 * Contains details of employee such as ID, name, DOB, etc.
 * Includes department and collection of certificate(s) done
 * by the employee.
 * </p>
 *
 * @author Gowtham R
 * @version 1.0
 */
@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", unique = true)
    private long phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany
    @JoinTable(name = "employee_certificates",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    private Set<Certificate> certificates;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}

