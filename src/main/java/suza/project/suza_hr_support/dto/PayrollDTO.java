package suza.project.suza_hr_support.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import suza.project.suza_hr_support.enums.PayrollStatus;

@Data
public class PayrollDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long employeeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String departmentName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String positionName;

    // =========================
    // Payroll period
    // =========================
    // private Integer payrollMonth;   // user selects
    // private Integer payrollYear;    // user selects

    private LocalDate payrollDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer basicSalary;

    private Integer housingAllowance;
    private Integer transportAllowance;

    private Integer paye;
    private Integer nssf;
    private Integer nhif;
    private Integer loanDeduction;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer grossSalary;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalDeduction;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer netSalary;

 
    private PayrollStatus status;

    // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    // private LocalDate paymentDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String currency;
}
