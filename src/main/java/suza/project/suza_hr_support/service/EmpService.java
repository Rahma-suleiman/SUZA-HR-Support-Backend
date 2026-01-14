package suza.project.suza_hr_support.service;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.EmpDTO;
import suza.project.suza_hr_support.entity.Attendance;
import suza.project.suza_hr_support.entity.Department;
import suza.project.suza_hr_support.entity.Employee;
import suza.project.suza_hr_support.entity.LeaveRequest;
import suza.project.suza_hr_support.enums.EmployeeStatusEnum;
import suza.project.suza_hr_support.repository.DeptRepository;
import suza.project.suza_hr_support.repository.EmpRepository;

@Service
@AllArgsConstructor
public class EmpService {

        private final ModelMapper modelMapper;
        private final EmpRepository empRepository;
        private final DeptRepository deptRepository;

        public List<EmpDTO> getAllEmployee() {
                List<Employee> employees = empRepository.findAll();
                return employees.stream()
                                .map(emp -> {
                                        EmpDTO empDto = modelMapper.map(emp, EmpDTO.class);

                                        // FK
                                        if (emp.getDepartment() != null) {
                                                empDto.setDepartmentId(emp.getDepartment().getId());
                                        }

                                        // REVERSE R/SHIPS mappings
                                        empDto.setLeaveRequestIds(emp.getLeaveRequests() != null
                                                        ? emp.getLeaveRequests().stream()
                                                                        .map(LeaveRequest::getId)
                                                                        .collect(Collectors.toList())
                                                        : List.of());
                                        empDto.setAttendanceIds(emp.getAttendances() != null
                                                        ? emp.getAttendances().stream()
                                                                        .map(Attendance::getId)
                                                                        .collect(Collectors.toList())
                                                        : List.of());
                                        return empDto;
                                }).collect(Collectors.toList());
        }

        public EmpDTO getEmployeeById(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("employee not found with id" + id));

                EmpDTO empDto = modelMapper.map(emp, EmpDTO.class);

                // FK

                // Set department ID
                if (emp.getDepartment() != null) {
                        empDto.setDepartmentId(emp.getDepartment().getId());
                }

                // REVERSE R/SHIPS mappings

                empDto.setLeaveRequestIds(emp.getLeaveRequests() != null
                                ? emp.getLeaveRequests().stream()
                                                .map(LeaveRequest::getId)
                                                .collect(Collectors.toList())
                                : List.of());
                                
                empDto.setAttendanceIds(emp.getAttendances() != null
                                ? emp.getAttendances().stream()
                                                .map(Attendance::getId)
                                                .collect(Collectors.toList())
                                : List.of());
                return empDto;
        }

        public EmpDTO createEmployee(EmpDTO empDTO) {
                Employee employee = modelMapper.map(empDTO, Employee.class);

                // Department required
                if (empDTO.getDepartmentId() == null) {
                        throw new IllegalStateException("Department is required for every employee");
                }

                Department department = deptRepository.findById(empDTO.getDepartmentId())
                                .orElseThrow(() -> new IllegalStateException("Department not found"));

                employee.setDepartment(department); // employee has its department set

                // Set hire date (use payload if provided, otherwise today)
                if (empDTO.getHireDate() != null) {
                        employee.setHireDate(empDTO.getHireDate());
                } else {
                        employee.setHireDate(LocalDate.now());
                }

                // Generate Employee Number
                String year = String.valueOf(LocalDate.now().getYear());
                Long count = empRepository.count() + 1;
                String empNumber = String.format("EMP/%s/%04d", year, count);
                employee.setEmpNo(empNumber);

                // Save employee (this will persist the manager relation too)
                Employee savedEmployee = empRepository.save(employee);

                // Map response DTO
                EmpDTO empResponse = modelMapper.map(savedEmployee, EmpDTO.class);
                empResponse.setDepartmentId(
                                savedEmployee.getDepartment() != null ? savedEmployee.getDepartment().getId() : null);


                return empResponse;
        }

        public EmpDTO editEmployee(Long id, EmpDTO empDTO) {
                Employee existingEmp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("Employee not found with ID:" + id));
                empDTO.setId(existingEmp.getId());

                modelMapper.map(empDTO, existingEmp);

                if (empDTO.getDepartmentId() != null) {
                        Department dept = deptRepository.findById(empDTO.getDepartmentId())
                                        .orElseThrow(() -> new IllegalStateException(
                                                        "Invalid department id" + empDTO.getDepartmentId()));
                        existingEmp.setDepartment(dept);
                }
                Employee updatedEmp = empRepository.save(existingEmp);

                EmpDTO empDtoResponse = modelMapper.map(updatedEmp, EmpDTO.class);
        
                empDtoResponse.setDepartmentId(
                                updatedEmp.getDepartment() != null ? updatedEmp.getDepartment().getId() : null);

                return empDtoResponse;
        }



        // RECOMMENDED
        // ✅ Instead, you would SOFT DELETE (mark as INACTIVE or TERMINATED) so history
        // stays intact.
        // This performs a soft delete (marks employee as INACTIVE instead of removing
        // them permanently

        @Transactional
        public void deactivateEmployee(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("Employee not found with id " + id));

                if (emp.getStatus() == EmployeeStatusEnum.INACTIVE) {
                        throw new IllegalStateException("Employee with id " + id + " is already inactive");
                }
                emp.setStatus(EmployeeStatusEnum.INACTIVE);
                empRepository.save(emp);
        }

        @Transactional
        public void activateEmployee(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("Employee not found with id " + id));

                if (emp.getStatus() == EmployeeStatusEnum.ACTIVE) {
                        throw new IllegalStateException("Employee with id " + id + " is already active");
                }
                emp.setStatus(EmployeeStatusEnum.ACTIVE);
                empRepository.save(emp);
        }

        // Not really. Employees are usually never HARD-DELETED, because you need
        // their history (who worked, reviews, promotions, etc.).

        // Delete employee → automatically delete recruitment + children
        // BUT make sure in employee entity all FK with @OneToMany(mappedBy = "",
        // cascade = CascadeType.ALL, orphanRemoval = true)
        // AND in recruitment u shld hv smthing like this
        // @OneToOne(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval=
        // true)
        @Transactional
        public void deleteEmployeeById(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("Employee not found with ID: " + id));
                empRepository.delete(emp); // Cascade will remove subordinates and reviews
        }
}

// POST
// {
// "firstName": "Rahma",
// "lastName": "Suleiman",
// "email": "rahma.suleiman@example.com",
// "phone": "+255712345678",
// "address": "Mombasa",
// "gender": "Female",
// "dob": "1998-04-15",
// "hireDate": "2023-06-01",
// "position": "Software Engineer",
// "salary": 1200000,
// "status": "ACTIVE",
// "departmentId": 2
// }
// {
// "firstName": "Amina",
// "lastName": "Hassan",
// "email": "amina.hassan@example.com",
// "phone": "+255712345678",
// "address": "Dar es Salaam",
// "gender": "Female",
// "dob": "1995-06-12",
// "hireDate": "2023-03-15",
// "position": "Software Engineer",
// "salary": 1500000,
// "status": "ACTIVE",
// "managerId": 1,
// "departmentId": 3
// }
// {
// "firstName": "Amina",
// "lastName": "Haron",
// "email": "emm@example.com",
// "phone": "+255765256701",
// "address": "Vuga",
// "gender": "Female",
// "dob": "1998-04-15",
// "hireDate": "2023-08-01",
// "position": "Marketing",
// "salary": 1400000,
// "status": "ACTIVE",
// "managerId": 2,
// "departmentId": 2
// }
// {
// "firstName": "Kauthar",
// "lastName": "Pongwa",
// "email": "pongwa@example.com",
// "phone": "+2556790608733",
// "address": "Saateni",
// "gender": "Female",
// "dob": "2004-09-19",
// "hireDate": "2025-09-19",
// "position": "Accoun",
// "salary": 1200000,
// "status": "ACTIVE",
// "managerId": 1,
// "departmentId": 1
// }