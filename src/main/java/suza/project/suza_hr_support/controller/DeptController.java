package suza.project.suza_hr_support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import suza.project.suza_hr_support.dto.DeptDTO;
import suza.project.suza_hr_support.dto.EmpDTO;
import suza.project.suza_hr_support.service.DeptService;

// import hm.project.hrsupport.dto.DeptDTO;
// import hm.project.hrsupport.dto.EmpDTO;
// import hm.project.hrsupport.service.DeptService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v2/hrsupport/department")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public ResponseEntity<List<DeptDTO>> getAllDepartment() {
        List<DeptDTO> department = deptService.getAllDepartment();
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DeptDTO> getDeptById(@PathVariable Long id) {
        DeptDTO deptId = deptService.getDeptById(id);
        return ResponseEntity.ok(deptId);
    }
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<EmpDTO>> getDeptByIdEmps(@PathVariable Long id) {
        List<EmpDTO> empDTOs = deptService.getDeptByIdEmps(id);
        return ResponseEntity.ok(empDTOs);
    }
    @PostMapping
    public ResponseEntity<DeptDTO> creatDepartment(@RequestBody DeptDTO deptDTO) {
        DeptDTO department = deptService.creatDepartment(deptDTO);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DeptDTO> editDepartment(@PathVariable Long id, @RequestBody DeptDTO deptDTO){
        DeptDTO department = deptService.editDepartment(id, deptDTO);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id){
        deptService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
    
}
