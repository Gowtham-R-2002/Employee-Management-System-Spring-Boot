package com.i2i.ems.department;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentDao departmentDao;

    private Department responseDepartment;
    private Department requestDepartment;
    private Department responseDepartmentWithEmployees;

    @BeforeEach
    public void setup() {
        requestDepartment = Department.builder()
                .name("CSE")
                .build();
        responseDepartment = Department.builder()
                .id(1L)
                .name("CSE")
                .isDeleted(false)
                .employees(Collections.emptySet())
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
    public void testGetDepartments() {
        when(departmentDao.findByIsDeletedFalse()).thenReturn(Collections.singletonList(responseDepartment));
        List<Department> departments = departmentService.getDepartments();
        assertEquals(1, departments.size());
    }

    @Test
    public void testGetDepartmentById() {
        when(departmentDao.findByIdAndIsDeleted(1L, false)).thenReturn(responseDepartment);
        Department department = departmentService.getDepartmentById(1L);
        assertEquals(1L, department.getId());
    }

    @Test
    public void testGetDepartmentById_returnsNull() {
        when(departmentDao.findByIdAndIsDeleted(1L, false)).thenReturn(null);
        assertThrows(EmployeeException.class, () -> departmentService.getDepartmentById(1L));
    }

    @Test
    public void testAddOrUpdateDepartment() {
        when(departmentDao.save(requestDepartment)).thenReturn(responseDepartment);
        Department department = departmentService.addOrUpdateDepartment(requestDepartment);
        assertEquals(1L, department.getId());
    }

    @Test
    public void testGetDepartmentWithEmployees() {
        when(departmentDao.getDepartmentWithEmployees(1L)).thenReturn(responseDepartmentWithEmployees);
        Department department = departmentService.getDepartmentWithEmployees(1L);
        assertEquals(1L, department.getId());
        assertEquals(1, department.getEmployees().size());
    }
}
