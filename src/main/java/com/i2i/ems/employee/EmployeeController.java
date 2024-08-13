package com.i2i.ems.employee;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
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

import com.i2i.ems.certificate.CertificateService;
import com.i2i.ems.department.DepartmentService;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

/**
 * <p>
 * Provides endpoint for handling operations on employee such as adding an employee.
 * </p>
 *
 * @author Gowtham R
 * @version 1.4
 */
@CrossOrigin
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private static final Logger logger = LogManager.getLogger();
    private final CertificateService certificateService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService, CertificateService certificateService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.certificateService = certificateService;
    }

    /**
     * <p>
     * Returns the all available employees
     * </p>
     * @return  {@link ResponseEntity<EmployeeDto>}
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> displayEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees().stream()
                .map(employee -> EmployeeMapper.toEmployeeDto(employee)).collect(Collectors.toList());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * <p>
     *     Gets the new employee data and saves it
     * </p>
      * @param employeeDto {@link EmployeeDto}
     * @return  {@link ResponseEntity<EmployeeDto>}
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        Department department = null;
        department = departmentService.getDepartmentById(employeeDto.getDepartmentId());
        if(null == department) {
            logger.info("Department with ID : {} not found !", employeeDto.getDepartmentId());
            throw new EmployeeException("Department not found with ID : " +  employeeDto.getDepartmentId());
        }
        System.out.println(employeeDto.getName());
        Employee employee = EmployeeMapper.toEmployee(employeeDto, department);
        Employee savedEmployee = employeeService.addOrUpdateEmployee(employee);
        return new ResponseEntity<>(EmployeeMapper.toEmployeeDto(savedEmployee), HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Gets the data of the specific ID from the user
     * </p>
     * @param id The ID of the employee to be searched
     * @return  {@link ResponseEntity<EmployeeDto>}
     */
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "id") Long id){
        Employee employee;
        employee = employeeService.getEmployeeById(id);
        if (null == employee) {
            logger.info("Employee with ID : {} not found !", id);
            throw new EmployeeException("Employee not found with ID : " + id );
        }
        return new ResponseEntity<>(EmployeeMapper.toEmployeeDto(employee), HttpStatus.OK);
    }

    /**
     * <p>
     *     Updates an existing employee with the new data
     * </p>
     * @param employeeDto  {@link EmployeeDto}
     * @return  {@link ResponseEntity<EmployeeDto>}
     */
    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {;
        if(null ==  employeeService.getEmployeeById(employeeDto.getId())) {
            logger.info("Employee with ID : {} not found !", employeeDto.getId());
            throw new EmployeeException("Employee not found with ID : " + employeeDto.getId() );
        }
        if(null == departmentService.getDepartmentById(employeeDto.getDepartmentId())) {
            logger.info("Department with ID : {} not found !", employeeDto.getDepartmentId());
            throw new EmployeeException("Department not found with ID : " + employeeDto.getDepartmentId() );
        }
        Employee employee = EmployeeMapper.toEmployee(employeeDto,
                departmentService.getDepartmentById(employeeDto.getDepartmentId()));
        Employee savedEmployee = employeeService.addOrUpdateEmployee(employee);
        return new ResponseEntity<>(EmployeeMapper.toEmployeeDto(savedEmployee), HttpStatus.OK);
    }

    /**
     * <p>
     *     Soft deletes the specific employee with ID provided
     * </p>
     * @param id The ID of the employee to be deleted
     * @return  {@link ResponseEntity<EmployeeDto>}
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus.Series> deleteEmployee(@PathVariable(name = "id") Long id) {
        Employee employee;
        employee = employeeService.getEmployeeById(id);
        if (null == employee) {
            logger.info("Employee with ID : {} not found !", id);
            throw new EmployeeException("Employee not found with ID : " + id );
        }
        employee.setDeleted(true);
        EmployeeMapper.toEmployeeDto(employeeService.addOrUpdateEmployee(employee));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     *     Assigns a certificate to an selected employee
     * </p>
     * @param id  The ID of the employee to be assigned with a certificate
     * @param certificateId  The ID of the certificate to be assigned to the
     *                       employee
     * @return {@link ResponseEntity<EmployeeDto>}
     */
    @PutMapping("{id}/certificate/{certificateId}")
    public ResponseEntity<EmployeeDto> addCertificateToEmployee(@PathVariable(name = "id") Long id,
                                                                @PathVariable(name = "certificateId") Long certificateId){
        if (null == employeeService.getEmployeeById(id)){
            logger.info("Employee with ID : {} not found !", id);
            throw new EmployeeException("Employee not found with ID : " + id );
        }
        if (null == certificateService.getCertificate(certificateId)){
            logger.info("Certificate with ID : {} not found !", certificateId);
            throw new EmployeeException("Certificate not found with ID : " + certificateId );
        }
        Employee updatedEmployee = certificateService.addEmployee(employeeService.getEmployeeById(id),certificateService.getCertificate(certificateId));
        return new ResponseEntity<>(EmployeeMapper.toEmployeeDto(updatedEmployee), HttpStatus.OK);
    }


}