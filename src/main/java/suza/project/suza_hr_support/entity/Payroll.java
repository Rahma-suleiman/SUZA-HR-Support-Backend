package suza.project.suza_hr_support.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import suza.project.suza_hr_support.enums.PayrollStatus;

@Data
@Entity
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK - Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Payroll date (month/year can be derived from this)
    private LocalDate date;

    // Salary
    private Integer basicSalary;

    // Allowances
    private Integer transportAllowance;
    private Integer housingAllowance;

    // Deductions
    private Integer paye;
    private Integer nssf;
    private Integer nhif;

    // Totals
    private Integer grossSalary;
    private Integer totalDeduction;
    private Integer netSalary;

    // Payroll status
    @Enumerated(EnumType.STRING)
    private PayrollStatus status;

    // Payment date
    private LocalDate paymentDate;
}
