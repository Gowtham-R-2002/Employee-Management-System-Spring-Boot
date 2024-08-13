package com.i2i.ems.certificate;

import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * Handles operations on certificate such as fetch, create, etc.
 * </p>
 *
 * @author Gowtham R
 * @version 1.4
 */
@Repository
public interface CertificateDao extends CrudRepository<Certificate, Long> {
    String getCertificateWithEmployees = "From Certificate certificate RIGHT JOIN FETCH"
            + " certificate.employees WHERE certificate.id = :id";

    Certificate findByIdAndIsDeleted(Long id, boolean isDeleted);


    List<Certificate> findByIsDeletedFalse();

    /**
     * <p>
     *     Fetches the employees associated with the given certificate ID
     * </p>
     * @param id  The ID of the certificate from which employees
     *            needed to be returned
     * @return  The certificate along with the associated employees
     */
    @Query(getCertificateWithEmployees)
    Certificate fetchCertificateWithEmployees(@Param("id") Long id);

}