package com.i2i.ems.employee;

import java.util.List;

import com.i2i.ems.model.Employee;

/** 
 * Handles operations on employee such as adding, deleting, etc
 * @author Gowtham R
 * @version  1.4
 */
public interface EmployeeService {

    /**
     * <p>
     *     Creates a new employee or updates it if already exists.
     * </p>
     * @param employee The employee to be saved or updated
     * @return  The saved or updated employee
     */
    Employee addOrUpdateEmployee(Employee employee);

    /**
     * <p>
     *     Gets the employee of the specific ID
     * </p>
     * @param id  The ID of the employee to be searched for.
     * @return  The found employee
     */
    Employee getEmployeeById(Long id);

    /**
     * <p>
     *     Gets the all available employees
     * </p>
     * @return The employees available
     */
    List<Employee> getAllEmployees();

}