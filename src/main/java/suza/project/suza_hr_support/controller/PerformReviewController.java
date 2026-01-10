package suza.project.suza_hr_support.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.PerformReviewDTO;
import suza.project.suza_hr_support.service.PerformReviewService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/review")
public class PerformReviewController {
    private final PerformReviewService performReviewService;

    @GetMapping
    public ResponseEntity<List<PerformReviewDTO>> getAllReviews() {
        List<PerformReviewDTO> review = performReviewService.getAllReviews();
        return ResponseEntity.ok(review);
        // return ResponseEntity.ok(performReviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity<PerformReviewDTO> createReview(@RequestBody PerformReviewDTO performReviewDTO) {
        PerformReviewDTO reviewDTO = performReviewService.createReview(performReviewDTO);
        return ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformReviewDTO> getReviewById(@PathVariable Long id) {
        PerformReviewDTO review = performReviewService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformReviewDTO> editReview(@PathVariable Long id, @RequestBody PerformReviewDTO reviewDTO) {
        PerformReviewDTO review = performReviewService.editReview(id, reviewDTO);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        performReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
