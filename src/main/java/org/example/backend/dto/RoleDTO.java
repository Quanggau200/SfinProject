package org.example.backend.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private UUID id;
    private String roleName;
}

