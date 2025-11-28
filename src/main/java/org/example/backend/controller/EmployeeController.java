package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<Page<EmployeeDto>>> getAllEmployee(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDto> result=employeeService.getALlEmployees(pageable);
        return ResponseEntity.ok(new ApiResponse<>(200,"success","get employees successfully",result));
    }

    @PostMapping("")
    @Operation(summary = "Create new employee")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Create Employee Successfully")
    public ResponseEntity<ApiResponse<EmployeeReponse>> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "success", "create employee successfully", employeeService.createEmployees(employeeRequest))
        );
    }
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable("employeeId") String employeeId) {
        return ResponseEntity.ok(new ApiResponse<>(200,"success","get detail employee successfully",employeeService.getEmployeeDetail(employeeId)));
    }

    @DeleteMapping("/{employeeId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ResponseEntity<Void>> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteByEmployeeId(employeeId);

        return new ApiResponse<>(200, "Success", "Delete Successfully", ResponseEntity.noContent().build());
    }
    @PatchMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> editEmployee(@PathVariable String employeeId,@RequestBody Map<String,Object> request)
    {
        Employees update=employeeService.updateEmployee(employeeId,request);
        EmployeeDto result=mapper.map(update,EmployeeDto.class);
        return ResponseEntity.ok(new ApiResponse<>(200,"Success","Update Successfully",result));
    }
}
