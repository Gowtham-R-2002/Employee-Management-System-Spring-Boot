package com.i2i.ems.department;

import java.util.*;
import java.util.stream.Collectors;

import com.i2i.ems.employee.EmployeeDto;
import com.i2i.ems.employee.EmployeeMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Responsible for providing endpoints for operations on department such as create, read, etc.
 * </p>
 *
 * @author Gowtham R
 * @version 1.4
 */
@CrossOrigin
@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
    private static final Logger logger = LogManager.getLogger();
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * <p>
     * Displays all available departments.
     * </p>
     * @return  {@link ResponseEntity<List<DepartmentDto>>}
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> displayDepartments() {
        List<DepartmentDto> departmentsDto = new ArrayList<>();
        List<Department> departments = departmentService.getDepartments();
        departmentsDto = departments.stream().map((department -> DepartmentMapper.toDepartmentDto(department))).collect(Collectors.toList());
        return new ResponseEntity<>(departmentsDto, HttpStatus.OK);
    }


    /**
     * <p>
     * Gets a specific Department from available departments
     * </p>
     *
     * @param id The ID of the Department
     * @return  {@link ResponseEntity<DepartmentDto>}
     */
    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "id") Long id) {
        DepartmentDto departmentDto = null;
        Department department = departmentService.getDepartmentById(id);
        if (null == department) {
            logger.info("Department with ID : {} not found !", id);
            throw new EmployeeException("Department not found with ID : " + id );
        }
        departmentDto = DepartmentMapper.toDepartmentDto(department);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    /**
     * <p>
     * Gets the details of the Department and creates a new Department
     * </p>
     *
     * @param DepartmentDto {@link DepartmentDto}
     * @return {@link ResponseEntity<DepartmentDto>}
     */
    @PostMapping
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = DepartmentMapper.toDepartment(departmentDto);
        Department savedDepartment = departmentService.addOrUpdateDepartment(department);
        logger.info("Department added success !");
        return new ResponseEntity<>(DepartmentMapper.toDepartmentDto(savedDepartment), HttpStatus.CREATED);
    }

    /**
     * <p>
     * Updates the name of the selected department
     *</p>
     * @param DepartmentDto {@link DepartmentDto}
     * @return  {@link ResponseEntity<DepartmentDto>}
     */
    @PutMapping
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = null;
        department = departmentService.getDepartmentById(departmentDto.getId());
        if (null == department) {
            logger.info("Department with ID : {} not found !", departmentDto.getId());
            throw new EmployeeException("Department not found with ID : " + departmentDto.getId() );
        }
        department.setName(departmentDto.getName());
        Department savedDepartment = departmentService.addOrUpdateDepartment(department);
        logger.info("Department update success !");
        return new ResponseEntity<>(DepartmentMapper.toDepartmentDto(savedDepartment), HttpStatus.ACCEPTED);
    }

    /**
     * <p>
     * Soft Deletes the selected department
     * </p>
     *
     * @param id The ID of the department to be deleted.
     * @return  {@link ResponseEntity<DepartmentDto>}
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus.Series> deleteDepartment(@PathVariable(name = "id") Long id) {
        Department department = null;
        department = departmentService.getDepartmentById(id);
        if (null == department) {
            logger.info("Department with ID : {} not found !", id);
            throw new EmployeeException("Department not found with ID : " + id );
        }
        department.setDeleted(true);
        departmentService.addOrUpdateDepartment(department);
        logger.info("Department delete success !");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     * Gets all employees associated with the specified department
     * <p/>
     *
     * @param id The ID of the specified department
     * @return  {@link ResponseEntity<List<EmployeeDto>>}
     */
    @GetMapping("{id}/employees")
    public ResponseEntity<List<EmployeeDto>> getDepartmentWithEmployees(@PathVariable(name = "id") Long id) {
        Department department = null;
        department = departmentService.getDepartmentWithEmployees(id);
        if (null == department) {
            logger.info("Department with ID : {} not found !", id);
            throw new EmployeeException("Department not found with ID : " + id );
        }
        List<EmployeeDto> employees = department.getEmployees().stream()
                .map(employee -> EmployeeMapper.toEmployeeDto(employee)).collect(Collectors.toList());

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}