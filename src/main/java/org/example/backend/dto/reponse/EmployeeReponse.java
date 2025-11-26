package org.example.backend.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeReponse {
    private String fullName;
    private String email;
    private String phone;
    @NotBlank
    private String employeeId;
    private String role_company;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")

    private LocalDate dataBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")

    private LocalDate hireDate;
    private String position;
    @NotBlank
    private String tax;
    private String  status;
    @NotBlank
    private List<String> payroll;

    private Integer salary;
    private String bankInfor;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")

    private LocalDate created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")

    private LocalDate updated_at;

}
