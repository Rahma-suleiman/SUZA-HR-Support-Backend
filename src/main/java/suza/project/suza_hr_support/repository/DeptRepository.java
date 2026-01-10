package suza.project.suza_hr_support.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import suza.project.suza_hr_support.entity.Department;

@Repository
public interface DeptRepository extends JpaRepository<Department, Long>{

    Optional<Department> findByName(String name);
    
}
