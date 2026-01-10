package suza.project.suza_hr_support.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Handles exceptions globally
public class ApiExceptionHandler {

    // Handle custom ApiRequestException
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    // Handle invalid enum values (for AttendanceStatus, Rating, etc.)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnum(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("AttendanceStatus")) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid status value. Allowed values: PRESENT, ABSENT, LATE, ON_LEAVE");
        }
        if (ex.getMessage().contains("Rating")) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid rating. Allowed values: EXCELLENT, GOOD, AVERAGE, POOR");
        }
        // Default case
        return ResponseEntity.badRequest().body("Invalid request data.");
    }
}

