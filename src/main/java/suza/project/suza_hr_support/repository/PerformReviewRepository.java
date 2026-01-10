package suza.project.suza_hr_support.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import suza.project.suza_hr_support.entity.PerformanceReview;


@Repository
public interface PerformReviewRepository extends JpaRepository<PerformanceReview, Long>{

    // List<PerformanceReview> findByReveiwerId(Long id);

    List<PerformanceReview> findByReviewerId(Long id);
    
}
