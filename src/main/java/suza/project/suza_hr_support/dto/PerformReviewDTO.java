package suza.project.suza_hr_support.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
// import jakarta.validation.constraints.NotNull;
import lombok.Data;
import suza.project.suza_hr_support.enums.RatingReviewEnum;

@Data
public class PerformReviewDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Date reviewDate;

    private String comments;

    // @NotNull(message = "Rating is required")
    private RatingReviewEnum rating;

    private Long employeeId;

    private Long reviewerId;
}
