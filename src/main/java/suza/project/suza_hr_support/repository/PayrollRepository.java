package suza.project.suza_hr_support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suza.project.suza_hr_support.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
