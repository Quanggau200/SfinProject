package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.SpecificationDTO.EmployeePayrollDTO;
import org.example.backend.persitence.repository.EmployeesRepository;
import org.modelmapper.ModelMapper;
import org.example.backend.dto.EmployeeDto;
import org.example.backend.dto.reponse.ApiResponse;
import org.example.backend.dto.reponse.EmployeeReponse;
import org.example.backend.dto.request.EmployeeRequest;
import org.example.backend.persitence.entity.Employees;
import org.example.backend.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backend.configuration.ModuleMapperConfig;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/sfinvietnam/employees")
@ResponseStatus
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService ;
    private final ModelMapper mapper;
    private final EmployeesRepository employeesRepository;
    @GetMapping("")
    public ApiResponse<Page<EmployeeDto>> getALl(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDto> result=  employeeService.getALlEmployees(pageable);
        return new ApiResponse<>( 200, "success","get employees successfully", result);
    }

    @PostMapping("")
    @Operation(summary = "")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Create Employee Successfully")
    public ApiResponse<EmployeeReponse> createEmployee(@RequestBody EmployeeRequest request)
    {
        
        return new ApiResponse<>(200,"Success","Create Successfully",employeeService.createEmployees(request));
    }

    @DeleteMapping("/{employeeId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ResponseEntity<Void>> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteByEmployeeId(employeeId);

        return new ApiResponse<>(200, "Success", "Delete Successfully", ResponseEntity.noContent().build());
    }

    @PatchMapping("/{employeeId}")
    public ApiResponse<EmployeeDto> editEmployee(
            @PathVariable String employeeId,
            @RequestBody Map<String, Object> request) {

        Employees updated = employeeService.updateEmployee(employeeId, request);
        EmployeeDto dto = mapper.map(updated, EmployeeDto.class);
        return new ApiResponse<>(200, "Success", "Cập nhật thành công", dto);
    }
}
