package suza.project.suza_hr_support.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import suza.project.suza_hr_support.enums.LeaveStatusEnum;
import suza.project.suza_hr_support.enums.LeaveTypeEnum;

@Getter
@Setter
@Table(name = "leave_request")
@Entity
public class LeaveRequest extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum status = LeaveStatusEnum.PENDING;

    private Date startDate;

    private Date endDate;

    private String reason;
    
    private String empName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

}
