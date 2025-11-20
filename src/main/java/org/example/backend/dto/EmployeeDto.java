package org.example.backend.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.backend.persitence.entity.Employees;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private UUID id;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must be less than 100 characters")
    private String fullName;
    @NotBlank
    @Email(message = "Email is not invaild")
    private String email;
    @Size(max = 15, message = "Phone must be less than 15 characters")
    private String phone;
    @NotBlank
    private String employeeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat
    private LocalDate dataBirth;
    @NotBlank
    private String role_company;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat
    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    @Size(max = 50, message = "Position must be less than 50 characters")
    private String position;

    @Size(max = 50, message = "Department must be less than 50 characters")
    private String department;

    @NotBlank
    private Integer salary;


    @Size(max=100,message = "Tax must be less than 100 characters")
    private String status;

    @Size(max=100,message = "Bank Infor must be less than 100 characters")
    private String bankInfor;


    @Size(max=100,message = "Tax must be less than 50 characters")
    private String tax;

    private String profileImage;

    @Size(max = 100, message = "Emergency contact name must be less than 100 characters")
    private String emergencyContactName;

    @Size(max = 15, message = "Emergency contact phone must be less than 15 characters")
    private String emergencyContactPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate updated_at;

//    private List<PayrollDto> employees;
}
