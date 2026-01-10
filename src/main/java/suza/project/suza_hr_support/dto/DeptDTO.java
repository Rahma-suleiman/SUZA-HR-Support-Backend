package suza.project.suza_hr_support.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeptDTO {
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    private String name;
    
    // reverse r/ships
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> employeeIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> jobPostingIds ;


}
