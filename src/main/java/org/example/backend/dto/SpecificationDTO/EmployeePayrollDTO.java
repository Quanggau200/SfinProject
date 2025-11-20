package org.example.backend.dto.SpecificationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayrollDTO {
    @NotBlank
    private String employeeId;
    @NotBlank
    private String name;
    @NotBlank
    private String role;
    @NotBlank
    private BigDecimal basicSalary;
    @NotBlank
    private BigDecimal bonus;
}
