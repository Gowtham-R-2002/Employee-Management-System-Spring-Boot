package com.i2i.ems.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * <p>
 *     Class used for storing and getting data as DTO for department .
 *     Contains only necessary fields that are needed
 *     to be displayed and received from the user.
 * </p>
 *
 * @author  Gowtham R
 * @version 1.4
 */
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    @NonNull
    @Pattern(regexp = "^[ A-Za-z]+$", message = "Test")
    @NotBlank(message = "Department name cannot be blank!")
    private String name;
}
