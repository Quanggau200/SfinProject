package org.example.backend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.example.backend.Mapper.EmployeeMapper;
import org.example.backend.dto.EmployeeDto;
import org.example.backend.dto.PayrollDto;
import org.example.backend.dto.reponse.ApiResponse;
import org.example.backend.dto.reponse.EmployeeReponse;
import org.example.backend.dto.request.EmployeeRequest;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.Payroll;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.EmployeesRepository;
import org.example.backend.persitence.repository.PayrollRepository;
import org.example.backend.persitence.repository.UserRepository;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmployeeService  {
    private final EmployeesRepository employeesRepository;
   private final EmployeeMapper employeeMapper;

    @GetMapping("")
    public Page<EmployeeDto> getALlEmployees(Pageable pageable) {
      return  employeesRepository.findAll(pageable)
              .map(employeeMapper::todo);

    }
    public EmployeeReponse createEmployees(EmployeeRequest request)
    {
        log.info("In method postEmployee");
//        List<Payroll> payrollList=payrollRepository.findPayrollByEmployeeId(request.getEmployeeId());
        if(request==null)
        {
            throw new IllegalArgumentException("Employee request is null");
        }
        if(employeesRepository.existsByEmail(request.getEmail()))
        {
            throw new IllegalArgumentException("Email is already in use");
        }
        if(employeesRepository.existsByFullName(request.getFullName())){
            throw new IllegalArgumentException("Username is already in use");
        }
        if(employeesRepository.existsByPhone(request.getPhone()))
        {
            throw new IllegalArgumentException("Phone number is already in use");
        }
        Employees employees = new Employees();
        employees.setEmployeeId(request.getEmployeeId());
        employees.setFullName(request.getFullName());
        employees.setEmail(request.getEmail());
        employees.setPhone(request.getPhone());
        employees.setDataBirth(request.getDataBirth());
        employees.setRole_company(request.getRole_company());
        employees.setHireDate(request.getHireDate());
        employees.setPosition(request.getPosition());
        employees.setStatus(request.getStatus());
        employees.setCreatedAt(request.getCreated_at());
        employees.setUpdatedAt(request.getUpdated_at());
        employees.setBankinfor(request.getBankInfor());
        employees.setTax(request.getTax());
        employees.setSalary(request.getSalary());

        employeesRepository.save(employees);
        EmployeeReponse response = new EmployeeReponse();

        response.setFullName(employees.getFullName());
        response.setEmail(employees.getEmail());
        response.setEmployeeId(employees.getEmployeeId());
        response.setPhone(employees.getPhone());
        response.setDataBirth(employees.getDataBirth());
        response.setRole_company(employees.getRole_company());
        response.setHireDate(employees.getHireDate());
        response.setStatus(employees.getStatus());
        response.setCreated_at(employees.getCreatedAt());
        response.setUpdated_at(employees.getUpdatedAt());
        response.setBankInfor(employees.getBankinfor());
        response.setTax(employees.getTax());
        response.setSalary(employees.getSalary());
        return response;
    };

    public Employees updateEmployee(String employeeId  ,Map<String,Object> request)
    {
        log.info("In method updateEmployee");
        Employees entity=employeesRepository.findByEmployeeId(employeeId).orElseThrow(()->new EntityNotFoundException("Employee not found: " + employeeId));
        request.forEach((field, value) -> {
            if (value == null) return;

            try {
                if (value instanceof String str && isDateField(field)) {
                    LocalDate valueToSet = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    PropertyUtils.setProperty(entity, field, valueToSet);
                }
                else
                {
                    PropertyUtils.setProperty(entity, field, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Lỗi cập nhật: " + field, e);
            }
        });
        entity.setCreatedAt(LocalDate.now());
        entity.setUpdatedAt(LocalDate.now());
        return employeesRepository.save(entity);

    }
    public EmployeeDto getEmployeeDetail(String employeeid)
    {
        Employees employees=employeesRepository.findByEmployeeId(employeeid).
                orElseThrow(()->new EntityNotFoundException("Employee not found: " + employeeid));
        return employeeMapper.todo(employees);
    }


    public void deleteByEmployeeId(String employeeId) {
        if(!employeesRepository.existsById(employeeId))
        {
            throw new IllegalArgumentException("Employee id not found");
        }
        employeesRepository.deleteById(employeeId);

    }


    private boolean isDateField(String fieldName) {
        return Set.of("dataBirth", "hireDate", "createdAt", "updatedAt").contains(fieldName);
    }

}
