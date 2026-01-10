package suza.project.suza_hr_support.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import suza.project.suza_hr_support.dto.DeptDTO;
import suza.project.suza_hr_support.dto.EmpDTO;
import suza.project.suza_hr_support.entity.Department;
import suza.project.suza_hr_support.entity.Employee;
import suza.project.suza_hr_support.exception.ApiRequestException;
import suza.project.suza_hr_support.repository.DeptRepository;


@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private ModelMapper modelMapper;

    // public List<DeptDTO> getAllDepartment() {
    // List<Department> depts = deptRepository.findAll();
    // // ModelMapper here will map all fields of Department, including the full
    // // Employee entities
    // // fetched from the DB — not recommended if we only need their IDs.
    // return depts.stream()
    // .map(dept -> modelMapper.map(dept, DeptDTO.class))
    // .collect(Collectors.toList());
    // }

    public List<DeptDTO> getAllDepartment() {
        List<Department> depts = deptRepository.findAll();
        return depts.stream()
                .map(dept -> {
                    DeptDTO deptDto = modelMapper.map(dept, DeptDTO.class);
                    // We set only employee IDs here.
                    // Without this, ModelMapper would try to map the entire Employee entities
                    // (all fields), but in this DTO we only want their IDs.
                    // REVERSE R/SHIPS
                    deptDto.setEmployeeIds(dept.getEmployees() != null
                            ? dept.getEmployees().stream()
                                    .map(Employee::getId)
                                    .collect(Collectors.toList())
                            : List.of()); // empty list if no emp found
                    return deptDto;
                })
                .collect(Collectors.toList());

    }

    // public DeptDTO getDeptById(Long id) {
    // Department deptId = deptRepository.findById(id)
    // .orElseThrow(() -> new ApiRequestException("Department not found with id" +
    // id));
    // return modelMapper.map(deptId, DeptDTO.class);
    // }
    public DeptDTO getDeptById(Long id) {
        Department dept = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department not found with id" + id));

        DeptDTO deptDto = modelMapper.map(dept, DeptDTO.class);

        // REVERSE R/SHIPS
        deptDto.setEmployeeIds(dept.getEmployees() != null
                ? dept.getEmployees().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
                : List.of());
        return deptDto;
    }

    public DeptDTO creatDepartment(DeptDTO deptDTO) {
        Department department = modelMapper.map(deptDTO, Department.class);
        // check if department exists by name
        Optional<Department> isDeptExist = deptRepository.findByName(deptDTO.getName());
        if (isDeptExist.isPresent()) {
            // throw new ApiRequestException("department exist");
            throw new ApiRequestException("Department with name " + deptDTO.getName() + " already exists");
        }

        Department saveDept = deptRepository.save(department);
        return modelMapper.map(saveDept, DeptDTO.class);
    }

    public DeptDTO editDepartment(Long id, DeptDTO deptDTO) {
        Department existingDepart = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department ID not found"));
        deptDTO.setId(existingDepart.getId());
        modelMapper.map(deptDTO, existingDepart);
        Department updatedDept = deptRepository.save(existingDepart);
        return modelMapper.map(updatedDept, DeptDTO.class);
    }

    public void deleteDepartment(Long id) {
        deptRepository.deleteById(id);
    }

    public List<EmpDTO> getDeptByIdEmps(Long id) {
        Department department = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department not found"));
        return department.getEmployees().stream()
                .map(emp -> {
                    EmpDTO empDto = modelMapper.map(emp, EmpDTO.class);

                    empDto.setDepartmentId(emp.getDepartment().getId());
                    return empDto;
                })
                .collect(Collectors.toList());
    }

}

// {
// "name": "IT"
// }
// {
// "name": "HR"
// },
// {
// "name": "Accountancy"
// },
// {
// "name": "Science"
// },
// {
// "name": "Agriculture"
// },
// {
// "name": "Finance"
// },
// {
// "name": "Procurement"
// },
// {
// "name": "Production"
// },
// {
// "name": "Sale and Marketing"
// }