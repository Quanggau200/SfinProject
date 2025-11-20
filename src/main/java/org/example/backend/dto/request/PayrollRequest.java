package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayrollRequest {
    private UUID id;
    @NotBlank
    private String employee_id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String role_company;
    @NotBlank
    private BigDecimal salary;
    @NotBlank
    private BigDecimal bonus;
    @NotBlank
    private String status;
}
