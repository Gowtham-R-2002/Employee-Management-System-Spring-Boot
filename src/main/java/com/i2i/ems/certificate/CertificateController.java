package com.i2i.ems.certificate;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.ems.employee.EmployeeDto;
import com.i2i.ems.employee.EmployeeMapper;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

/** 
 * <p> Responsible for providing endpoints for 
 * CRUD operation on certificate
 * </p>
 * @author   Gowtham R
 * @version  1.4
 */
@CrossOrigin
@RestController
@RequestMapping("api/v1/certificates")
public class CertificateController {
    private static final Logger logger = LogManager.getLogger();
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * <p>
     *     Gets the certificate data from the user and saves it.
     * </p>
     * @param certificateDto {@link CertificateDto}
     * @return {@link ResponseEntity<CertificateDto>}
     */
    @PostMapping
    public ResponseEntity<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto){
        Certificate certificate = CertificateMapper.toCertificate(certificateDto);
        Certificate savedCertificate = certificateService.addOrUpdateCertificate(certificate);
        return new ResponseEntity<>(CertificateMapper.toCertificateDto(savedCertificate), HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Gets all available certificates
     * </p>
     * @return {@link ResponseEntity<List<CertificateDto>}
     */
    @GetMapping
    public ResponseEntity<List<CertificateDto>> getCertificates() {
        List<CertificateDto> certificates = certificateService.getCertificates().stream()
                .map(certificate -> CertificateMapper.toCertificateDto(certificate)).collect(Collectors.toList());
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * <p>
     *     Updates the selected certificate with the new data
     * </p>
     * @param certificateDto {@link CertificateDto}
     * @return  {@link ResponseEntity<CertificateDto>}
     */
    @PutMapping
    public ResponseEntity<CertificateDto> updateCertificate(@RequestBody CertificateDto certificateDto) {
        if (null == certificateService.getCertificate(certificateDto.getId())){
            logger.info("Certificate with ID : {} not found !", certificateDto.getId());
            throw new EmployeeException("Certificate with ID : " + certificateDto.getId() + " not found !");
        }
        Certificate certificate = certificateService.getCertificate(certificateDto.getId());
        certificate.setName(certificateDto.getName());
        Certificate savedCertificate = certificateService.addOrUpdateCertificate(certificate);
        return new ResponseEntity<>(CertificateMapper.toCertificateDto(savedCertificate), HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     *     Soft deletes the selected certificate and returns it
     * </p>
     * @param id  The ID of the certificate to be deleted.
     * @return  {@link ResponseEntity<CertificateDto>}
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus.Series> deleteCertificate(@PathVariable(name = "id") Long id) {
        if (null == certificateService.getCertificate(id)){
            logger.info("Certificate with ID : {} not found !", id);
            throw new EmployeeException("Certificate with ID : " + id + " not found !");
        }
        Certificate certificate = certificateService.getCertificate(id);
        certificate.setDeleted(true);
        certificateService.addOrUpdateCertificate(certificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     *     Gets the employees associated with the certificate
     * </p>
     * @param id  The ID of the certificate from where employees
     *            needed to be displayed.
     * @return  {@link ResponseEntity<CertificateDto>}
     */
    @GetMapping("{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@PathVariable(name = "id") Long id) {
        if (null == certificateService.getCertificate(id)){
            logger.info("Certificate with ID : {} not found !", id);
            throw new EmployeeException("Certificate with ID : " + id + " not found !");
        }
        List<EmployeeDto> employees = certificateService.getCertificateEmployees(id).stream()
                .map(employee -> EmployeeMapper.toEmployeeDto(employee)).collect(Collectors.toUnmodifiableList());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}