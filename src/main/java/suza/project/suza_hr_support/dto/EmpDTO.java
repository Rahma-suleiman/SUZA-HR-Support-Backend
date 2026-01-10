package suza.project.suza_hr_support.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmpDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String empNo;

    // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate hireDate;
    private String position; 
    private Integer salary;
    private String status; 

    // FK
    private Long managerId; 

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Only visible in response
    private List<Long> subordinateIds; 

    private Long departmentId;

    
    // Reviews received
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> receivedReviewIds;
    // private List<PerformReviewDTO> receivedReviews;
    
    // Reviews written
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> writtenReviewIds;
    // private List<PerformReviewDTO> writtenReviews;
    

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long payroll;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> leaveRequestIds ;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> attendanceIds ;

    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long recruitmentId; // Only present if employee came from recruitment
}
