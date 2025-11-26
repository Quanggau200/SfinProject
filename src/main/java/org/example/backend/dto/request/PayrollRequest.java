package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PayrollRequest {
    private UUID id;
    @NotBlank
    private String employeeId;
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
    private LocalDateTime createdAt;
}
