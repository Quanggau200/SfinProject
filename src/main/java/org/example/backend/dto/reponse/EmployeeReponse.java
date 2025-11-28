package org.example.backend.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeReponse {
    @NotBlank
    private String employeeId;
    @NotBlank
    private String fullName;
    @NotBlank
    private String address;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
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
    private Integer salary;
    @NotBlank
    private String bankInfor;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate created_at;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate updated_at;


}
