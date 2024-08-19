package com.i2i.ems.certificate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.i2i.ems.employee.EmployeeService;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceTest {

    @Mock
    private CertificateDao certificateDao;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private CertificateServiceImpl certificateService;


    private Certificate requestCertificate;
    private Certificate responseCertificate;
    private Department department;
    private Address address;
    private Employee employee;

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
        employee = Employee.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .certificates(new HashSet<>())
                .build();
        requestCertificate = Certificate.builder()
                .name("AWS")
                .build();
        Set<Employee> employees = new HashSet<>();
        employees.add(employee);
        responseCertificate = Certificate.builder()
                .id(1L)
                .name("AWS")
                .isDeleted(false)
                .employees(employees)
                .build();
    }

    @Test
    public void testAddOrUpdateCertificate() {
        when(certificateDao.save(requestCertificate)).thenReturn(responseCertificate);
        Certificate certificate = certificateService.addOrUpdateCertificate(requestCertificate);
        assertNotNull(certificate.getId());
    }

    @Test
    public void testAddOrUpdateCertificate_returnNull() {
        when(certificateDao.save(requestCertificate)).thenReturn(null);
        assertThrows(EmployeeException.class, () -> certificateService.addOrUpdateCertificate(requestCertificate));
    }

    @Test
    public void testGetCertificate() {
        when(certificateDao.findByIdAndIsDeleted(1L, false)).thenReturn(responseCertificate);
        Certificate certificate = certificateService.getCertificate(1L);
        assertEquals(1L, certificate.getId());
    }

    @Test
    public void testGetCertificate_null() {
        when(certificateDao.findByIdAndIsDeleted(1L, false)).thenReturn(null);
        assertThrows(EmployeeException.class, () -> certificateService.getCertificate(1L));
    }

    @Test
    public void testGetCertificates() {
        when(certificateDao.findByIsDeletedFalse()).thenReturn(Collections.singletonList(responseCertificate));
        List<Certificate> certificates = certificateService.getCertificates();
        assertEquals(1, certificates.size());
    }

    @Test
    public void testAddEmployee() {
        Set<Certificate> certificates = new HashSet<>();
        certificates.add(responseCertificate);
        Employee savedEmployee = Employee.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .certificates(certificates)
                .build();
        when(certificateDao.save(responseCertificate)).thenReturn(null);
        when(employeeService.addOrUpdateEmployee(employee)).thenReturn(savedEmployee);
        Employee returnedEmployee = certificateService.addEmployee(employee, responseCertificate);
        assertEquals(1, returnedEmployee.getCertificates().size());
    }

    @Test
    public void testGetCertificateEmployees() {
        when(certificateDao.fetchCertificateWithEmployees(1L)).thenReturn(responseCertificate);
        Set<Employee> employees = certificateService.getCertificateEmployees(1L);
        assertEquals(1, employees.size());
    }
}
