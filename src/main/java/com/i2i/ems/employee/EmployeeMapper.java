package com.i2i.ems.employee;

import com.i2i.ems.certificate.CertificateMapper;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.util.DateUtil;

import java.util.HashSet;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static Employee toEmployee(EmployeeDto employeeDto, Department department) {
        Address address = Address.builder()
                .id(employeeDto.getAddressId())
                .locality(employeeDto.getLocality())
                .doorNumber(employeeDto.getDoorNumber())
                .city(employeeDto.getCity())
                .build();
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .dateOfBirth(employeeDto.getDateOfBirth())
                .phoneNumber(employeeDto.getPhoneNumber())
                .address(address)
                .department(department)
                .build();
    }

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dateOfBirth(employee.getDateOfBirth())
                .phoneNumber(employee.getPhoneNumber())
                .addressId(employee.getAddress().getId())
                .doorNumber(employee.getAddress().getDoorNumber())
                .locality(employee.getAddress().getLocality())
                .city(employee.getAddress().getCity())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .certificatesDto(employee.getCertificates() == null ?
                        new HashSet<>() : employee.getCertificates().stream()
                        .map(CertificateMapper::toCertificateDto).collect(Collectors.toSet()))
                .age(DateUtil.getAge(employee.getDateOfBirth()))
                .build();
    }
}
