package org.example.backend.Mapper;

import org.example.backend.dto.EmployeeDto;
import org.example.backend.dto.PayrollDto;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
@Mapper(componentModel  = "spring")
public interface EmployeeMapper {
    @Mapping(target = "payroll",source = "payroll",qualifiedByName = "mapPayrollList")
    EmployeeDto todo(Employees employee);
    @Named("mapPayrollList")
    default List<PayrollDto> mapPayrollList(List<Payroll> payrollList) {
        if(payrollList==null)return new ArrayList<>();
        return payrollList.stream()
                .map(this::toPayrollDto)
                .toList();
    }
    PayrollDto toPayrollDto(Payroll payroll);
}
