package com.i2i.ems.certificate;

import java.util.List;
import java.util.Set;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

/** 
 * <p>
 * Provides methods for CRUD operations in certificates
 * </p>
 * @author   Gowtham R
 * @version  1.4
 */
public interface CertificateService {

    /** 
     * <p>
     * Adds a certificate to the available certificates.
     * </p>
     * 
     * @param certificate  The Certificate to be added
     */
    Certificate addOrUpdateCertificate(Certificate certificate);

    /**
     * Gets a specific Certificate from the available certificates
     *
     * @param id  The ID of the certificate to be searched
     * @return    The found Certificate
     */
    Certificate getCertificate(Long id) ;

    /**
     * Gets all available certificates.
     * 
     * @return               All available certificates
     */
    List<Certificate> getCertificates() ;

    /**
     * Adds an employee to the Certificate and vice versa
     * 
     * @param certificate        The Certificate in which the employee will
     *                           be associated.
     * @param employee  The Employee to which the certificate will be added
     */
    Employee addEmployee(Employee employee, Certificate certificate) ;

    /**
     * <p>
     * Gets employees of a particular associated with a particular certificate.
     * </p>
     * @param id                  The ID of the certificate from where employees
                                  need to be fetched.
     * @return                    The employees associated with a particular certificate.
     */     
    Set<Employee> getCertificateEmployees(Long id);
}