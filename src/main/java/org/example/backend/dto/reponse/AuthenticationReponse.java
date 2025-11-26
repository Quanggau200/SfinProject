package org.example.backend.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class AuthenticationReponse {
    private String token;
    private String refreshToken;
    private boolean authenticated;
}
