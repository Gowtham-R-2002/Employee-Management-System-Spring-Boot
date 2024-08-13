package com.i2i.ems.department;

import java.util.List;

import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    String getEmployeesQuery = "From Department department RIGHT JOIN FETCH department.employees WHERE department.id = :id";

    /**
     * <p>
     * Gets the employees of specific department
     * </p>
     * @param id           The ID of the department from which the employees are fetched.
     * @return             The employees associated with the department
     */
    @Query(getEmployeesQuery)
    Department getDepartmentWithEmployees(@Param("id") Long id);

    List<Department> findByIsDeletedFalse();

    Department findByIdAndIsDeleted(Long id, boolean isDeleted);

}
