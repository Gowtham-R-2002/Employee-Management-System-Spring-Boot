package com.i2i.ems;

import com.i2i.ems.department.DepartmentService;
import com.i2i.ems.employee.EmployeeController;
import com.i2i.ems.employee.EmployeeDao;
import com.i2i.ems.employee.EmployeeDto;
import com.i2i.ems.employee.EmployeeService;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeController {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Mock
    DepartmentService departmentService;


    private Employee requestEmployee;
    private Employee responseEmployee;
    private EmployeeDto requestEmployeeDto;
    private EmployeeDto responseEmployeeDto;
    private Employee savedEmployee;
    private Department department;
    private Address address;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .id(1L)
                .name("CSE")
                .isDeleted(false)
                .build();
        address = Address.builder()
                .locality("Madurai")
                .doorNumber("1/90")
                .city("TN")
                .build();
        requestEmployeeDto = EmployeeDto.builder()
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .departmentId(1L)
                .doorNumber("1/90")
                .locality("Madurai")
                .city("TN")
                .build();

        responseEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .departmentId(1L)
                .addressId(1L)
                .departmentName("CSE")
                .doorNumber("1/90")
                .locality("Madurai")
                .city("TN")
                .age("22y 0m")
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
    public void testAddEmployee() {
        lenient().when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(employeeService.addOrUpdateEmployee(requestEmployee)).thenReturn(responseEmployee);
        ResponseEntity<EmployeeDto> responseEntity = employeeController.addEmployee(requestEmployeeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(responseEmployeeDto.getId());
    }
}
