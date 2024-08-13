package com.i2i.ems.employee;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import com.i2i.ems.annotation.PhoneNumber;
import com.i2i.ems.certificate.CertificateDto;

/**
 * <p>
 *     Class used for storing and getting data as DTO for employee.
 *     Contains only necessary fields that are needed
 *     to be displayed and received from the user.
 * </p>
 *
 * @author  Gowtham R
 * @version 1.4
 */
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class EmployeeDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    @NonNull
    @NotBlank(message = "Employee name cannot be blank!")
    @Pattern(regexp = "^[ A-Za-z]+$")
    private String name;

    @NonNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @JsonProperty
    @PhoneNumber
    private long phoneNumber;

    @JsonProperty
    @NonNull
    private Long departmentId;

    @JsonProperty
    private String departmentName;

    @JsonProperty
    private Long addressId;

    @JsonProperty
    @NonNull
    @NotBlank(message = "Door number cannot be empty!")
    private String doorNumber;

    @JsonProperty
    @NonNull
    @NotBlank(message = "Locality cannot be empty!")
    private String locality;

    @JsonProperty
    @NonNull
    @NotBlank(message = "City name must contain alphabets only")
    @Pattern(regexp = "^[ A-Za-z]+$")
    private String city;

    @JsonProperty("certificates")
    private Set<CertificateDto> certificatesDto;

    @JsonProperty
    private String age;
}
