package com.i2i.ems.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.ems.employee.EmployeeDto;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CertificateController.class)
public class CertificateControllerTest {
    @MockBean
    private CertificateService certificateService;

    @Autowired
    private MockMvc mockMvc;

    private Certificate requestCertificate;
    private Certificate responseCertificate;
    private CertificateDto requestCertificateDto;
    private Department department;
    private Address address;
    private Employee responseEmployee;
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
        responseEmployee = Employee.builder()
                .id(1L)
                .name("Gowtham")
                .phoneNumber(8531911113L)
                .dateOfBirth(LocalDate.of(2002, 8, 8))
                .department(department)
                .address(address)
                .build();
        requestCertificate = Certificate.builder()
                .name("AWS")
                .build();
        responseCertificate = Certificate.builder()
                .id(1L)
                .name("AWS")
                .isDeleted(false)
                .build();
        requestCertificateDto = CertificateDto.builder()
                .name("AWS")
                .build();
    }

    @Test
    public void testAddCertificate() {
        when(certificateService.addOrUpdateCertificate(any(Certificate.class))).thenReturn(responseCertificate);
        try {
            mockMvc.perform(post("/api/v1/certificates")
                    .content(new ObjectMapper().writeValueAsString(requestCertificateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetCertificates() {
        when(certificateService.getCertificates()).thenReturn(Collections.singletonList(responseCertificate));
        try {
            mockMvc.perform(get("/api/v1/certificates"))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateCertificate() {
        CertificateDto updatableCertificateDto = CertificateDto.builder()
                .id(1L)
                .name("AWS")
                .build();
        when(certificateService.getCertificate(1L)).thenReturn(responseCertificate);
        when(certificateService.addOrUpdateCertificate(any(Certificate.class))).thenReturn(responseCertificate);
        try {
            mockMvc.perform(put("/api/v1/certificates")
                    .content(new ObjectMapper().writeValueAsString(updatableCertificateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(status().isAccepted());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateCertificate_nullCertificate() {
        CertificateDto updatableCertificateDto = CertificateDto.builder()
                .id(1L)
                .name("AWS")
                .build();
        when(certificateService.getCertificate(1L)).thenReturn(null);
        try {
            mockMvc.perform(put("/api/v1/certificates")
                            .content(new ObjectMapper().writeValueAsString(updatableCertificateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Certificate with ID : 1 not found !"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteCertificate() {
        when(certificateService.getCertificate(1L)).thenReturn(responseCertificate);
        when(certificateService.addOrUpdateCertificate(responseCertificate)).thenReturn(null);
        try {
            mockMvc.perform(delete("/api/v1/certificates/1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteCertificate_nullCertificate() {
        when(certificateService.getCertificate(1L)).thenReturn(null);
        try {
            mockMvc.perform(delete("/api/v1/certificates/1"))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Certificate with ID : 1 not found !"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetEmployees() {
        when(certificateService.getCertificate(1L)).thenReturn(responseCertificate);
        when(certificateService.getCertificateEmployees(1L)).thenReturn(Collections.singleton(responseEmployee));
        try {
            mockMvc.perform(get("/api/v1/certificates/1/employees"))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetEmployees_nullCertificate() {
        when(certificateService.getCertificate(1L)).thenReturn(null);
        try {
            mockMvc.perform(get("/api/v1/certificates/1/employees"))
                    .andExpect(jsonPath("$.statusCode").value(404))
                    .andExpect(jsonPath("$.message").value("Certificate with ID : 1 not found !"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
