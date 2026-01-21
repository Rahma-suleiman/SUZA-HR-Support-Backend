package suza.project.suza_hr_support.dto;

import java.time.LocalDate;

import lombok.Data;
import suza.project.suza_hr_support.enums.PayrollStatus;

@Data
public class PayrollDTO {

    private Long id;

    private Long employeeId;
    private String employeeName; // optional but helpful

    private LocalDate date;

    private Integer basicSalary;
    private Integer transportAllowance;
    private Integer housingAllowance;

    private Integer paye;
    private Integer nssf;
    private Integer nhif;

    private Integer grossSalary;
    private Integer totalDeduction;
    private Integer netSalary;

    private PayrollStatus status;
    private LocalDate paymentDate;
}
