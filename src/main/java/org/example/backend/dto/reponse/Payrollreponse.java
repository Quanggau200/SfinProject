package org.example.backend.dto.reponse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.persitence.entity.Payroll;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payrollreponse {
    private UUID id;
    @NotBlank
    private String employeeId;
    @NotBlank
    private String employee_name;
    @NotBlank
    private String role_company;
    @NotBlank
    private BigDecimal salary;
    @NotBlank
    private BigDecimal bonus;
    @NotBlank
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
