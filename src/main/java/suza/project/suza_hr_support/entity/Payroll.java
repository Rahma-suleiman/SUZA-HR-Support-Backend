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

    // ========================
    // Employee Reference
    // ========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Snapshot fields (important for payslip history)
    private String employeeCode;      // EMP-0012
    private String employeeName;      // Rahma Suleiman
    private String departmentName;    // Accountancy
    private String positionName;      // Accountant

    // ========================
    // Payroll Period
    // ========================
    // private Integer payrollMonth;     // 1 - 12
    // private Integer payrollYear;      // 2026
    private LocalDate payrollDate;    

    // ========================
    // Earnings
    // ========================
    private Integer basicSalary;
    private Integer housingAllowance;
    private Integer transportAllowance;
    // private Integer overtimePay;

    private Integer grossSalary;

    // ========================
    // Deductions
    // ========================
    private Integer paye;
    private Integer nssf;
    private Integer nhif;
    private Integer loanDeduction;

    private Integer totalDeduction;

    // ========================
    // Net Pay
    // ========================
    private Integer netSalary;

    private String currency = "TZS";

    // ========================
    // Payroll Status
    // ========================
    @Enumerated(EnumType.STRING)
    private PayrollStatus status;

    // private LocalDate paymentDate;
}
