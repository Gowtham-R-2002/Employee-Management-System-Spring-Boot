package com.i2i.ems.employee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.i2i.ems.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee addOrUpdateEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeDao.findByIdAndIsDeleted(id, false);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAllEmployees();
    }
}