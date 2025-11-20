package org.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    @NotBlank(message = "Username is required")
    @Size(min=1, max=20,message = "Username must be at least at 6 characters")
    private String username;

    @NotBlank(message = "Username is required")
    @Email(message = "Email must be valid format ! !!")
    private String email;
    private Boolean active;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    Set<RoleDTO> roles;

    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message="Company is required")
    private String company;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate createAt=LocalDate.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate updateAt=LocalDate.now();
}
