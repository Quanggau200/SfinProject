package org.example.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @Id
    @NotBlank(message = "Username is required")
    @Email(message = "Email must be valid format ! !!")
    private String email;
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank
    private String password;
    @NotBlank (message = "Role is required")
    private String role;

}
