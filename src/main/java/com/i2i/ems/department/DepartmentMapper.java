package com.i2i.ems.department;

import com.i2i.ems.model.Department;

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
