package com.i2i.ems.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.i2i.ems.annotation.PhoneNumber;
import com.i2i.ems.certificate.CertificateDto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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
