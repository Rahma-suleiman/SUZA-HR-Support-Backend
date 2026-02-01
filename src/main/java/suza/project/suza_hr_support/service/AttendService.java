package suza.project.suza_hr_support.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import suza.project.suza_hr_support.dto.AttendDTO;
import suza.project.suza_hr_support.entity.Attendance;
import suza.project.suza_hr_support.entity.Employee;
import suza.project.suza_hr_support.repository.AttendRepository;
import suza.project.suza_hr_support.repository.EmpRepository;


@Service
public class AttendService {

    private AttendRepository attendRepository;
    private ModelMapper modelMapper;
    private EmpRepository empRepository;

    public AttendService(AttendRepository attendRepository, ModelMapper modelMapper, EmpRepository empRepository) {
        this.attendRepository = attendRepository;
        this.modelMapper = modelMapper;
        this.empRepository = empRepository;
    }


    public List<AttendDTO> getAllAttendance() {
        List<Attendance> attendance = attendRepository.findAll();
        return attendance.stream()
                .map(attend -> modelMapper.map(attend, AttendDTO.class))
                .collect(Collectors.toList());
    }


    public AttendDTO addAttendance(AttendDTO attendDTO) {
        Attendance attend = modelMapper.map(attendDTO, Attendance.class);

        Employee emp = empRepository.findById(attendDTO.getEmployeeId())
                .orElseThrow(()-> new IllegalStateException("Employee ID not found"));
        attend.setEmployee(emp);
        Attendance saveAttendance = attendRepository.save(attend);
        return modelMapper.map(saveAttendance, AttendDTO.class);
    }


    public AttendDTO getAttendanceById(Long id) {
        Attendance attend = attendRepository.findById(id)
                    .orElseThrow(()-> new IllegalStateException("attendance not found with id "+id));
        return modelMapper.map(attend, AttendDTO.class);
    }


    public void deleteAttendance(Long id) {
        attendRepository.deleteById(id);
    }


 public AttendDTO editAttendance(Long id, AttendDTO attendDTO) {
    // Fetch existing attendance
    Attendance attendance = attendRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Attendance not found"));

    // Fetch employee first
    Employee empAttend = empRepository.findById(attendDTO.getEmployeeId())
            .orElseThrow(() -> new IllegalStateException("Employee not found"));
    attendance.setEmployee(empAttend);

    // Map all fields from DTO to entity except employee
    modelMapper.map(attendDTO, attendance);

    // Set the employee explicitly

    // Save updated attendance
    Attendance savedAttendance = attendRepository.save(attendance);

    // Map back to DTO for response
    return modelMapper.map(savedAttendance, AttendDTO.class);
}


}
// {
//   "date": "2025-09-20",
//   "checkInTime": "08:15",
//   "checkOutTime": "17:30",
//   "status": "PRESENT",
//   "employeeId": 1
// }
// {
//     "date": "2025-09-20",
//     "checkInTime": "08:15",
//     "checkOutTime": "17:30",
//     "status": "PRESENT",
//     "employeeId": 2
// }