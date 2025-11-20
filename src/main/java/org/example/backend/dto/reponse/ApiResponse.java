package org.example.backend.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private String status;
    private int code;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse( int code,String status,String message,T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


}

