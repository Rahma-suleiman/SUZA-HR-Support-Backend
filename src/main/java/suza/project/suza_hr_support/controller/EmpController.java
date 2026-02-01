package suza.project.suza_hr_support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import suza.project.suza_hr_support.dto.EmpDTO;
import suza.project.suza_hr_support.service.EmpService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v2/hrsupport/employee")
// @CrossOrigin(origins = "http://localhost:5173")  // Allow React frontend (for testing purpose)
// http://localhost:8080/api/v2/hrsupport/employee
public class EmpController {
    
    @Autowired
    private EmpService empService;
    
    @GetMapping
    public ResponseEntity<List<EmpDTO>> getAllEmployee() {
        List<EmpDTO> emp = empService.getAllEmployee();
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(empService.getEmployeeById(id));
    }
    @PostMapping
    public ResponseEntity<EmpDTO> addEmployee(@RequestBody EmpDTO empDTO) {
        EmpDTO emp = empService.createEmployee(empDTO);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpDTO> editEmployee(@PathVariable Long id, @RequestBody EmpDTO empDTO) {
        EmpDTO emp = empService.editEmployee(id, empDTO);
        return new ResponseEntity<>(emp,HttpStatus.OK);
    }

  
    //HARD/Permanent DELETE though not advisable
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        empService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee and related data permanently deleted");
    }
    
}

