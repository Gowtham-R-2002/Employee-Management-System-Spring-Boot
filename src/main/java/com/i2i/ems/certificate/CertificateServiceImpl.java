package com.i2i.ems.certificate;

import java.util.List;
import java.util.Set;

import com.i2i.ems.employee.EmployeeService;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDao certificateDao;
    private final EmployeeService employeeService;
    private static final Logger logger = LogManager.getLogger();

    public CertificateServiceImpl(CertificateDao certificateDao, EmployeeService employeeService) {
        this.certificateDao = certificateDao;
        this.employeeService = employeeService;
    }


    @Override
    public Certificate addOrUpdateCertificate(Certificate certificate) {
        Certificate savedCertificate = null;
        savedCertificate = certificateDao.save(certificate);
        if (null == savedCertificate) {
            logger.warn("Certificate add failed for ID : {}", certificate.getId());
            throw new EmployeeException("Certificate add failed for ID : " + certificate.getId());
        }
        return savedCertificate;
    }

    @Override
    public Certificate getCertificate(Long id) {
        Certificate certificate = null;
        certificate = certificateDao.findByIdAndIsDeleted(id, false);
        if (null == certificate) {
            logger.info("Cannot find certificate with ID : {}", id);
            throw new EmployeeException("Cannot find certificate with ID : " + id);
        }
        return certificate;
    }

    @Override
    public List<Certificate> getCertificates() {
        return certificateDao.findByIsDeletedFalse();
    }

    @Override
    public Employee addEmployee(Employee employee, Certificate certificate) {
        employee.getCertificates().add(certificate);
        certificate.getEmployees().add(employee);
        certificateDao.save(certificate);
        return employeeService.addOrUpdateEmployee(employee);
    }

    @Override
    public Set<Employee> getCertificateEmployees(Long id) {
        return certificateDao.fetchCertificateWithEmployees(id).getEmployees();
    }
}