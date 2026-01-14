package suza.project.suza_hr_support.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.LeaveRequestDTO;
import suza.project.suza_hr_support.entity.Employee;
import suza.project.suza_hr_support.entity.LeaveRequest;
import suza.project.suza_hr_support.repository.EmpRepository;
import suza.project.suza_hr_support.repository.LeaveRepository;

@Service
@AllArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    public LeaveRequestDTO createLeave(LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leave = modelMapper.map(leaveRequestDTO, LeaveRequest.class);

        Employee employee = empRepository.findById(leaveRequestDTO.getEmployeeId())
                .orElseThrow(
                        () -> new IllegalStateException("Employee not found with id" + leaveRequestDTO.getEmployeeId()));
        leave.setEmployee(employee);
        LeaveRequest savedLeave = leaveRepository.save(leave);
        LeaveRequestDTO leaveDtoResponse = modelMapper.map(savedLeave, LeaveRequestDTO.class);
        return leaveDtoResponse;
    }

    public List<LeaveRequestDTO> getAllLeaves() {
        List<LeaveRequest> leaves = leaveRepository.findAll();
        return leaves.stream()
                .map(leave -> {
                    LeaveRequestDTO leaveDto = modelMapper.map(leave, LeaveRequestDTO.class);
                    leaveDto.setEmployeeId(leave.getEmployee().getId());
                    return leaveDto;
                })
                .collect(Collectors.toList());
    }

    public LeaveRequestDTO editLeave(Long id, LeaveRequestDTO leaveRequestDTO) {

        LeaveRequest existingLeave = leaveRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Leave not found with id" + id));
            
        modelMapper.map(leaveRequestDTO, existingLeave);

        // existingLeave.setId(id);
        leaveRequestDTO.setId(existingLeave.getId());

        if (leaveRequestDTO.getEmployeeId() != null) {
            Employee employee = empRepository.findById(leaveRequestDTO.getEmployeeId())
                    .orElseThrow(() -> new IllegalStateException("Employee not found with id" + leaveRequestDTO.getEmployeeId()));
            existingLeave.setEmployee(employee);            
        }

        LeaveRequest savedLeave = leaveRepository.save(existingLeave);
        return modelMapper.map(savedLeave, LeaveRequestDTO.class);


    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
// {
// "leaveType": "SICK",
// "status": "APPROVED",
// "startDate": "2025-09-03T08:30:00.000Z",
// "endDate": "2025-09-05T17:00:00.000Z",
// "reason": "Flu and doctor's advice to rest",
// "employeeId": 1
// }
// {
//   "leaveType": "VACATION",
//   "status": "APPROVED",
//   "startDate": "2025-12-20T09:00:00.000Z",
//   "endDate": "2025-12-27T17:00:00.000Z",
//   "reason": "Family vacation",
//   "employeeId": 2
// }

