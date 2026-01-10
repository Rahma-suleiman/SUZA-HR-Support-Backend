package suza.project.suza_hr_support.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import suza.project.suza_hr_support.enums.AttendanceStatusEnum;


@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance extends AuditModel<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatusEnum status;

    // Many-to-One relationship: Many Attendance records for one Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false) // FK is on Attendance table
    private Employee employee;
}
