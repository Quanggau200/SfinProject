package org.example.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollDto {
    private UUID id;
    private String employeeName;
    private String employeeId;
    private BigDecimal basic_salary;
    private BigDecimal bonus;

}
