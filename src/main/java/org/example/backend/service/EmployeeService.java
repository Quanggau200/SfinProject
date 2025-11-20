package org.example.backend.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.example.backend.dto.EmployeeDto;
import org.example.backend.dto.reponse.ApiResponse;
import org.example.backend.dto.reponse.EmployeeReponse;
import org.example.backend.dto.request.EmployeeRequest;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.persitence.entity.User;
import org.example.backend.persitence.repository.EmployeesRepository;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeService {
    private final EmployeesRepository employeesRepository;
    private final ModelMapper mapper = new ModelMapper();
    public EmployeeService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }
    public Page<EmployeeDto> getALlEmployees(Pageable pageable) {
        Page<Employees> employeesPage =employeesRepository.findAll(pageable);
        return employeesPage.map(employee -> EmployeeDto.builder()
                .id(employee.getId())
                .employeeId(employee.getEmployeeId()) // thêm
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .dataBirth(employee.getDataBirth())
                .hireDate(employee.getHireDate()) // thêm
                .position(employee.getPosition()) // thêm
                .department(employee.getDepartment()) // thêm
                .role_company(employee.getRole_company())
                .status(employee.getStatus())
                .salary(employee.getSalary())
                .tax(employee.getTax())
                .bankInfor(employee.getBankInfor())
                .profileImage(employee.getProfileImage()) // thêm
                .created_at(employee.getCreatedAt())
                .updated_at(employee.getUpdatedAt())
                .build());

    }
    public EmployeeReponse createEmployees(EmployeeRequest request)
    {
        log.info("In method postEmployee");

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
        employees.setId(request.getId());
        employees.setFullName(request.getFullName());
        employees.setEmail(request.getEmail());
        employees.setEmployeeId(request.getEmployeeId());
        employees.setPhone(request.getPhone());
        employees.setDataBirth(request.getDataBirth());
        employees.setRole_company(request.getRole_company());
        employees.setHireDate(request.getHireDate());
        employees.setPosition(request.getPosition());
        employees.setStatus(request.getStatus());
        employees.setCreatedAt(request.getCreated_at());
        employees.setUpdatedAt(request.getUpdated_at());
        employees.setBankInfor(request.getBankInfor());
        employees.setTax(request.getTax());
        employees.setSalary(request.getSalary());

        employeesRepository.save(employees);
        EmployeeReponse response = new EmployeeReponse();

        response.setFullName(employees.getFullName());
        response.setEmail(employees.getEmail());
        response.setId(employees.getId());
        response.setPhone(employees.getPhone());
        response.setEmployeeId(employees.getEmployeeId());
        response.setDataBirth(employees.getDataBirth());
        response.setRole_company(employees.getRole_company());
        response.setHireDate(employees.getHireDate());
        response.setStatus(employees.getStatus());
        response.setCreated_at(employees.getCreatedAt());
        response.setUpdated_at(employees.getUpdatedAt());
        response.setBankInfor(employees.getBankInfor());
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


    public void deleteById(UUID id) {
        if(!employeesRepository.existsById(id))
        {
            throw new IllegalArgumentException("Employee id not found");
        }
        employeesRepository.deleteById(id);
        employeesRepository.deleteById(id);

    }


    private boolean isDateField(String fieldName) {
        return Set.of("dataBirth", "hireDate", "createdAt", "updatedAt").contains(fieldName);
    }

}
