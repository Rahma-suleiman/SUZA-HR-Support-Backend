package suza.project.suza_hr_support.service;

// import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.PayrollDTO;
import suza.project.suza_hr_support.entity.Employee;
import suza.project.suza_hr_support.entity.Payroll;
import suza.project.suza_hr_support.enums.PayrollStatus;
import suza.project.suza_hr_support.repository.EmpRepository;
import suza.project.suza_hr_support.repository.PayrollRepository;

@Service
@AllArgsConstructor
public class PayrollService {

    private final ModelMapper modelMapper;
    private final PayrollRepository payrollRepository;
    private final EmpRepository empRepository;

    public List<PayrollDTO> getAllPayrolls() {
        List<Payroll> payrolls = payrollRepository.findAll();
        return payrolls.stream().map(payroll -> {
            PayrollDTO dto = modelMapper.map(payroll, PayrollDTO.class);

            // FK
            if (payroll.getEmployee() != null) {
                dto.setEmployeeId(payroll.getEmployee().getId());
                dto.setEmployeeName(
                        payroll.getEmployee().getFirstName() + " " + payroll.getEmployee().getLastName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public PayrollDTO getPayrollById(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payroll not found with id " + id));

        PayrollDTO dto = modelMapper.map(payroll, PayrollDTO.class);

        if (payroll.getEmployee() != null) {
            dto.setEmployeeId(payroll.getEmployee().getId());
            dto.setEmployeeName(
                    payroll.getEmployee().getFirstName() + " " + payroll.getEmployee().getLastName());
        }
        return dto;
    }

    public PayrollDTO createPayroll(PayrollDTO dto) {
        Payroll payroll = modelMapper.map(dto, Payroll.class);

        // set employee
        if (dto.getEmployeeId() == null)
            throw new IllegalStateException("EmployeeId is required");

        Employee emp = empRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalStateException("Employee not found"));

        payroll.setEmployee(emp);

        // calculate totals
        payroll.setGrossSalary(calculateGross(payroll));
        payroll.setTotalDeduction(calculateDeduction(payroll));
        payroll.setNetSalary(payroll.getGrossSalary() - payroll.getTotalDeduction());

        // default status
        payroll.setStatus(dto.getStatus() == null ? PayrollStatus.DRAFT : dto.getStatus());

        Payroll saved = payrollRepository.save(payroll);

        return modelMapper.map(saved, PayrollDTO.class);
    }

    public PayrollDTO updatePayroll(Long id, PayrollDTO dto) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payroll not found with id " + id));

        modelMapper.map(dto, payroll);

        // set employee if provided
        if (dto.getEmployeeId() != null) {
            Employee emp = empRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new IllegalStateException("Employee not found"));
            payroll.setEmployee(emp);
        }

        payroll.setGrossSalary(calculateGross(payroll));
        payroll.setTotalDeduction(calculateDeduction(payroll));
        payroll.setNetSalary(payroll.getGrossSalary() - payroll.getTotalDeduction());

        Payroll updated = payrollRepository.save(payroll);
        return modelMapper.map(updated, PayrollDTO.class);
    }

    @Transactional
    public void deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payroll not found with id " + id));
        payrollRepository.delete(payroll);
    }

    // helper methods
    private Integer calculateGross(Payroll p) {
        return p.getBasicSalary()
                + (p.getTransportAllowance() == null ? 0 : p.getTransportAllowance())
                + (p.getHousingAllowance() == null ? 0 : p.getHousingAllowance());
    }

    private Integer calculateDeduction(Payroll p) {
        return (p.getPaye() == null ? 0 : p.getPaye())
                + (p.getNssf() == null ? 0 : p.getNssf())
                + (p.getNhif() == null ? 0 : p.getNhif());
    }
}
