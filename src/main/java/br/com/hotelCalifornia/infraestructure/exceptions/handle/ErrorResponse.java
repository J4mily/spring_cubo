package br.com.hotelCalifornia.infraestructure.exceptions.handle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private LocalDateTime data;
    private int status;
    private String path;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
