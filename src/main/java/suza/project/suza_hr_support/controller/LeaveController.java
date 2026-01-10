package suza.project.suza_hr_support.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import suza.project.suza_hr_support.dto.LeaveRequestDTO;
import suza.project.suza_hr_support.service.LeaveService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/leave")
public class LeaveController {
    private final LeaveService leaveService;
    
    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeave(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO leave = leaveService.createLeave(leaveRequestDTO);
        return new ResponseEntity<>(leave, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaves() {
        List<LeaveRequestDTO> leave = leaveService.getAllLeaves();
        return ResponseEntity.ok(leave);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> editLeave(@PathVariable Long id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO leaveDto = leaveService.editLeave(id, leaveRequestDTO);
        return ResponseEntity.ok(leaveDto);
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }
    
}
