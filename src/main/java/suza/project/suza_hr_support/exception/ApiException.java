package suza.project.suza_hr_support.exception;


import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class ApiException {
    private final String message;
    // private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(
                String message, 
                // Throwable throwable, 
                HttpStatus httpStatus, 
                ZonedDateTime timestamp) {
        this.message = message;
        // this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    
}
