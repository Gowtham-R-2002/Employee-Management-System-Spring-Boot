package com.i2i.ems.employee;

import java.time.LocalDate;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.i2i.ems.certificate.CertificateService;
import com.i2i.ems.department.DepartmentService;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    DepartmentService departmentService;

    @MockBean
    CertificateService certificateService;

    private Employee requestEmployee;
    private Employee responseEmployee;
    private EmployeeDto requestEmployeeDto;
    private EmployeeDto responseEmployeeDto;
    private Department department;
    private Address address;
    private Certificate certificate;

    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
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
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(employeeService.addOrUpdateEmployee(any(Employee.class))).thenReturn(responseEmployee);
        try {
            mockMvc.perform(post("/api/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestEmployeeDto)))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddEmployee_returnsEmployeeException() {
        when(departmentService.getDepartmentById(1L)).thenReturn(null);
        when(employeeService.addOrUpdateEmployee(any(Employee.class))).thenReturn(responseEmployee);
        try {
            mockMvc.perform(post("/api/v1/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestEmployeeDto)))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Department not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDisplayEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(responseEmployee));
        try {
            mockMvc.perform(get("/api/v1/employees"))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(responseEmployee);
        try {
            mockMvc.perform(get("/api/v1/employees/1"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetEmployeeById_returnsEmployeeException() {
        when(employeeService.getEmployeeById(1L)).thenReturn(null);
        try {
            mockMvc.perform(get("/api/v1/employees/1"))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Employee not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeDto updatableEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .departmentId(1L)
                .doorNumber("1/90")
                .locality("Madurai")
                .city("TN")
                .build();
        when(employeeService.getEmployeeById(1L)).thenReturn(responseEmployee);
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(employeeService.addOrUpdateEmployee(any(Employee.class))).thenReturn(responseEmployee);
        try {
           mockMvc.perform(put("/api/v1/employees")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(updatableEmployeeDto)))
                   .andExpect(jsonPath("$.id").value(1))
                   .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateEmployee_nullEmployee() {
        EmployeeDto updatableEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .departmentId(1L)
                .doorNumber("1/90")
                .locality("Madurai")
                .city("TN")
                .build();
        when(employeeService.getEmployeeById(1L)).thenReturn(null);
        try {
            mockMvc.perform(put("/api/v1/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatableEmployeeDto)))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Employee not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateEmployee_nullDepartment() {
        EmployeeDto updatableEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .departmentId(1L)
                .doorNumber("1/90")
                .locality("Madurai")
                .city("TN")
                .build();
        when(employeeService.getEmployeeById(1L)).thenReturn(responseEmployee);
        when(departmentService.getDepartmentById(1L)).thenReturn(null);
        try {
            mockMvc.perform(put("/api/v1/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatableEmployeeDto)))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Department not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(responseEmployee);
        when(employeeService.addOrUpdateEmployee(any(Employee.class))).thenReturn(responseEmployee);
        try {
            mockMvc.perform(delete("/api/v1/employees/1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteEmployee_nullEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(null);
        try {
            mockMvc.perform(delete("/api/v1/employees/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Employee not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddCertificateToEmployee() {
        Employee certificateAddedEmployee = Employee.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .certificates(Collections.singleton(certificate))
                .build();
        when(employeeService.getEmployeeById(1L)).thenReturn(requestEmployee);
        when(certificateService.getCertificate(1L)).thenReturn(certificate);
        when(certificateService.addEmployee(requestEmployee, certificate)).thenReturn(certificateAddedEmployee);
        try {
            mockMvc.perform(put("/api/v1/employees/1/certificate/1"))
                    .andExpect(jsonPath("$.certificates.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddCertificateToEmployee_nullEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(null);
        try {
            mockMvc.perform(put("/api/v1/employees/1/certificate/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Employee not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddCertificateToEmployee_nullCertificate() {
        when(employeeService.getEmployeeById(1L)).thenReturn(requestEmployee);
        when(certificateService.getCertificate(1L)).thenReturn(null);
        try {
            mockMvc.perform(put("/api/v1/employees/1/certificate/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Certificate not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
