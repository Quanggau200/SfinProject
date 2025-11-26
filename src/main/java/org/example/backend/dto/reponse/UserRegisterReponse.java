package org.example.backend.dto.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.dto.request.UserRegisterRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterReponse {
    private UUID id;
    @NotBlank(message = "Username is required")
    @Size(min=6,message = "Username must be at least 8 characters !")
    private String username;


    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in valid format !!!")
    private String email;

    private Boolean active;
    private Set<String> roles;
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message="Company is required")
    private String company;
    @NotBlank
    private String token;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime updateAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;


    public UserRegisterReponse(@Valid UserRegisterRequest request) {
    }
}
