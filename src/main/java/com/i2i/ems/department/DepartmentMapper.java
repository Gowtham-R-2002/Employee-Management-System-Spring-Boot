package com.i2i.ems.department;

import com.i2i.ems.model.Department;

/**
 * <p>
 * Class for converting department entity to DTO and vice versa.
 * </p>
 *
 * @author   Gowtham R
 * @version  1.4
 */
public class DepartmentMapper {
    public static Department toDepartment(DepartmentDto departmentDto) {
        return Department.builder()
               .name(departmentDto.getName())
               .build();
    }

    public static DepartmentDto toDepartmentDto(Department department) {
       return  DepartmentDto.builder()
               .id(department.getId())
               .name(department.getName())
               .build();
    }
}
