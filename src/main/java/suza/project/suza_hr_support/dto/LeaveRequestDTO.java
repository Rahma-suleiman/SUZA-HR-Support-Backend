package suza.project.suza_hr_support.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;
import suza.project.suza_hr_support.enums.LeaveStatusEnum;
import suza.project.suza_hr_support.enums.LeaveTypeEnum;

@Data
public class LeaveRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private LeaveTypeEnum leaveType;

    private LeaveStatusEnum status;

    private Date startDate;
    private Date endDate;

    private String reason;

    private Long employeeId;

}
