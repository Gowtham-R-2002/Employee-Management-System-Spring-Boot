package com.i2i.ems.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
    @NotEmpty
    @NotBlank(message = "Department name cannot be blank!")
    private String name;
}
