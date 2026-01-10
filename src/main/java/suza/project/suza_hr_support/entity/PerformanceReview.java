package suza.project.suza_hr_support.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

// import hm.project.hrsupport.enums.RatingReviewEnum;
import lombok.Getter;
import lombok.Setter;
import suza.project.suza_hr_support.enums.RatingReviewEnum;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "review")
public class PerformanceReview extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date reviewDate;

    private String comments;

    @Enumerated(EnumType.STRING)
    private RatingReviewEnum rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    // Reviewer (often the manager, but can be anyone in Employee table)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerId", nullable = false)
    private Employee reviewer;
}
