package com.i2i.ems.certificate;

import com.i2i.ems.model.Certificate;

/**
 * <p>
 * Class for converting certificate entity to DTO and vice versa.
 * </p>
 *
 * @author   Gowtham R
 * @version  1.4
 */
public class CertificateMapper {
    public static Certificate toCertificate(CertificateDto certificateDto) {
        return Certificate.builder()
                .id(certificateDto.getId())
                .name(certificateDto.getName())
                .build();
    }

    public static CertificateDto toCertificateDto(Certificate certificate) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .build();
    }
}
