package com.i2i.ems.department;

import java.util.List;


import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;
    private static final Logger logger = LogManager.getLogger();
    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> getDepartments(){
        return departmentDao.findByIsDeletedFalse();
    }
    @Override
    public Department getDepartmentById(Long id){
        Department department;
        department = departmentDao.findByIdAndIsDeleted(id, false);
        if (null == department) {
            logger.info("Department with ID : {} not found!" + id);
            throw new EmployeeException("Department not found with ID : " + id );
        }
        return department;
    }

    @Override
    public Department addOrUpdateDepartment(Department department) {
        return departmentDao.save(department);
    }

    @Override
    public Department getDepartmentWithEmployees(Long id) {
        return departmentDao.getDepartmentWithEmployees(id);
    }

}

