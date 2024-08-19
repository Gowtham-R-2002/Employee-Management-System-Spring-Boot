package com.i2i.ems.employee;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.i2i.ems.certificate.CertificateServiceImpl;
import com.i2i.ems.department.DepartmentServiceImpl;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private DepartmentServiceImpl departmentService;

    @Mock
    private CertificateServiceImpl certificateService;

    @Mock
    private EmployeeDao employeeDao;

    private Employee requestEmployee;
    private Employee responseEmployee;
    private Department department;
    private Address address;
    private Certificate certificate;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .id(1L)
                .name("CSE")
                .build();
        address = Address.builder()
                .id(1L)
                .locality("Madurai")
                .doorNumber("1/90")
                .city("TN")
                .build();
        certificate = Certificate.builder()
                .id(1L)
                .name("AWS")
                .build();

        requestEmployee = Employee.builder()
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .build();

        responseEmployee = Employee.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .build();
    }

    @Test
    public void testAddOrUpdateEmployee() {
        when(employeeDao.save(requestEmployee)).thenReturn(responseEmployee);
        Employee employee = employeeService.addOrUpdateEmployee(requestEmployee);
        assertEquals(1L, employee.getId());
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeDao.findByIdAndIsDeleted(1L, false)).thenReturn(responseEmployee);
        Employee employee = employeeService.getEmployeeById(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeDao.findAllEmployees()).thenReturn(Collections.singletonList(responseEmployee));
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(1, employees.size());
    }
}
