package com.i2i.ems.department;

import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department responseDepartment;
    private DepartmentDto requestDepartmentDto;
    private DepartmentDto responseDepartmentDto;
    private Department responseDepartmentWithEmployees;

    @BeforeEach
    public void setup() {
        requestDepartmentDto = DepartmentDto.builder()
                .name("CSE")
                .build();
        responseDepartment = Department.builder()
                .id(1L)
                .name("CSE")
                .isDeleted(false)
                .employees(Collections.emptySet())
                .build();
        responseDepartmentDto = DepartmentDto.builder()
                .id(1L)
                .name("CSE")
                .build();
        Set<Employee> employees = new HashSet<>();
        Address address = Address.builder()
                .id(1L)
                .doorNumber("1/66")
                .locality("Local")
                .city("madurai")
                .build();
        employees.add(Employee.builder()
                .name("User")
                .department(responseDepartment)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .address(address)
                .id(1L)
                .build());
        responseDepartmentWithEmployees = Department.builder()
                .id(1L)
                .name("CSE")
                .isDeleted(false)
                .employees(employees)
                .build();
    }

    @Test
    public void testAddDepartment() throws Exception {
        when(departmentService.addOrUpdateDepartment(any(Department.class))).thenReturn(responseDepartment);

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDepartmentDto)))
                .andExpect(status().isCreated())
                .andExpect(result -> assertNotNull(responseDepartmentDto.getId()));
    }

    @Test
    public void testAddDepartment_MethodArgumentNotValidException() {
        DepartmentDto invalidDepartmentDto = DepartmentDto.builder().name("003").build();
        try {
            mockMvc.perform(post("/api/v1/departments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(invalidDepartmentDto)))
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDisplayDepartments() {
        when(departmentService.getDepartments()).thenReturn(Collections.singletonList(responseDepartment));
        try {
            mockMvc.perform(get("/api/v1/departments")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDisplayDepartments_emptySizedListReturn() {
        when(departmentService.getDepartments()).thenReturn(Collections.emptyList());
        try {
            mockMvc.perform(get("/api/v1/departments")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()").value(0))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetDepartmentById() {
        when(departmentService.getDepartmentById(1L)).thenReturn(responseDepartment);
        try {
            mockMvc.perform(get("/api/v1/departments/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetDepartmentById_returnsNull() {
        when(departmentService.getDepartmentById(1L)).thenReturn(null);
        try {
            mockMvc.perform(get("/api/v1/departments/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Department not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateDepartment() {
        DepartmentDto updatableDepartmentDto = DepartmentDto.builder()
                .id(1L)
                .name("ECE")
                .build();
        when(departmentService.getDepartmentById(1L)).thenReturn(responseDepartment);
        when(departmentService.addOrUpdateDepartment(any(Department.class))).thenReturn(responseDepartment);
        try {
            mockMvc.perform(put("/api/v1/departments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(updatableDepartmentDto)))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isAccepted());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateDepartment_returnsNull() {
        DepartmentDto updatableDepartmentDto = DepartmentDto.builder()
                .id(1L)
                .name("ECE")
                .build();
        when(departmentService.getDepartmentById(1L)).thenReturn(null);
        when(departmentService.addOrUpdateDepartment(any(Department.class))).thenReturn(responseDepartment);
        try {
            mockMvc.perform(put("/api/v1/departments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(updatableDepartmentDto)))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Department not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteDepartment() {
        when(departmentService.getDepartmentWithEmployees(1L)).thenReturn(responseDepartment);
        when(departmentService.addOrUpdateDepartment(any(Department.class))).thenReturn(null);
        try {
            mockMvc.perform(delete("/api/v1/departments/1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteDepartment_returnsNull() {
        when(departmentService.getDepartmentWithEmployees(1L)).thenReturn(null);
        try {
            mockMvc.perform(delete("/api/v1/departments/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteDepartment_returnsAssociationException() {
        when(departmentService.getDepartmentWithEmployees(1L)).thenReturn(responseDepartmentWithEmployees);
        try {
            mockMvc.perform(delete("/api/v1/departments/1"))
                    .andExpect(jsonPath("$.statusCode").value(409))
                    .andExpect(jsonPath("$.message").value("Department has associated Employees ! Can't delete."))
                    .andExpect(status().isConflict());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetDepartmentWithEmployees() {
        when(departmentService.getDepartmentWithEmployees(1L)).thenReturn(responseDepartmentWithEmployees);
        try {
            mockMvc.perform(get("/api/v1/departments/1/employees"))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetDepartmentWithEmployees_returnsNull() {
        when(departmentService.getDepartmentWithEmployees(1L)).thenReturn(null);
        try {
            mockMvc.perform(get("/api/v1/departments/1/employees"))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Department not found with ID : 1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

