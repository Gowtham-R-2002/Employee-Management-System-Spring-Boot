package com.i2i.ems.certificate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * <p>
 *     Class used for storing and getting data as DTO for certificate and
 *     vice versa. Contains only necessary fields that are needed
 *     to be displayed to the user.
 * </p>
 *
 * @author  Gowtham R
 * @version 1.4
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateDto {
    @JsonProperty
    private Long id;

    @JsonProperty
    @NonNull
    @NotBlank(message = "Certificate name cannot be blank!")
    private String name;
}
