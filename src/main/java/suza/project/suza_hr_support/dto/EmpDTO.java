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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Only visible in response
    private List<Long> subordinateIds; 

    private Long departmentId;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long payroll;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> leaveRequestIds ;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> attendanceIds ;

}
