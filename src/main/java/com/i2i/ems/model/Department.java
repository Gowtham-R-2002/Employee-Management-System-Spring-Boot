package com.i2i.ems.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;


/**
 * <p>
 * Represents a department within an organisation.
 * It contains information about the department such as
 * name, and employees associated with it.
 * </p>
 * @author  Gowtham R
 * @version 1.0
 */
@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_name", unique = true)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;

}