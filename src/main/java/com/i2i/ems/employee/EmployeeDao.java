package com.i2i.ems.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.i2i.ems.model.Employee;

/** 
 * Handles operations on employee such as creating, updating, etc.
 * @author   Gowtham R
 * @version  1.0
 */
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    String getAllEmployeesQuery = "From Employee employee LEFT JOIN FETCH employee.department "
            + " WHERE employee.department.isDeleted = false and employee.isDeleted = false";

    Employee findByIdAndIsDeleted(Long id, boolean isDeleted);

    @Query(getAllEmployeesQuery)
    List<Employee> findAllEmployees();
}