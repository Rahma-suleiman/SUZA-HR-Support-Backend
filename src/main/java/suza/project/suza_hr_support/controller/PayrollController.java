package suza.project.suza_hr_support.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.PayrollDTO;
import suza.project.suza_hr_support.service.PayrollService;

@RestController
@RequestMapping("/api/v1/payroll")
@AllArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    public ResponseEntity<List<PayrollDTO>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDTO> getPayrollById(@PathVariable Long id) {
        return ResponseEntity.ok(payrollService.getPayrollById(id));
    }

    @PostMapping
    public ResponseEntity<PayrollDTO> createPayroll(@RequestBody PayrollDTO dto) {
        return ResponseEntity.ok(payrollService.createPayroll(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDTO> updatePayroll(@PathVariable Long id, @RequestBody PayrollDTO dto) {
        return ResponseEntity.ok(payrollService.updatePayroll(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.ok("Payroll deleted successfully");
    }
}
