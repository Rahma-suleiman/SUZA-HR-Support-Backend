package suza.project.suza_hr_support.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import suza.project.suza_hr_support.enums.EmployeeStatusEnum;

// @Data
@Getter
@Setter
@Entity
// @EqualsAndHashCode(callSuper = false)
@Table(name = "employee")
public class Employee extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    private LocalDate hireDate;
    private String position; // manager, CEO, software Developer
    private Integer salary;

    @Column(unique = true, nullable = false, updatable = false)
    private String empNo;

    @Enumerated(EnumType.STRING)
    private EmployeeStatusEnum status;

    // FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    // REVERSE R/SHIP MAPPING 
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LeaveRequest> leaveRequests = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();


}
