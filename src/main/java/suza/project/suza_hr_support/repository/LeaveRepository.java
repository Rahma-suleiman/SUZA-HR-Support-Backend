package suza.project.suza_hr_support.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import suza.project.suza_hr_support.entity.LeaveRequest;
import suza.project.suza_hr_support.enums.LeaveStatusEnum;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long>{

    List<LeaveRequest> findByStatus(LeaveStatusEnum approved);
    
}
