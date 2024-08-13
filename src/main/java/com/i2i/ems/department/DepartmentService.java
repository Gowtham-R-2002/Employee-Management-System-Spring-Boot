package com.i2i.ems.department;

import java.util.List;

import com.i2i.ems.model.Department;

/** 
 * <p>
 * Handles operations on Department such as adding department
 * </p>
 * @author   Gowtham R
 * @version  1.4
 */
public interface DepartmentService {

    /**
     * <p>
     * Gets all available Departments
     * </p>
     *
     * @return The Department data
     */
    List<Department> getDepartments();

    /**
     * <p>
     * Adds a department if not exists else updates the specified department.
     * </p>
     * @param department  The Department to be added or updated
     * @return            The added or updated department
     */
    Department addOrUpdateDepartment(Department department);

    /**
     * <p>
     * Gets all employees associated with the specified department
     * </p>
     * @param id  The ID of the Department from which the employees to be fetched.
     */
    Department getDepartmentWithEmployees(Long id);

    /**
     * <p>
     * Gets a specific department from the given ID
     * </p>
     * @param id                  The ID of the department to be searched for.
     */
    Department getDepartmentById(Long id);
}