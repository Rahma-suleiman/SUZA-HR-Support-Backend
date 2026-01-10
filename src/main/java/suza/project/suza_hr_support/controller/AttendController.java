package suza.project.suza_hr_support.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import suza.project.suza_hr_support.dto.AttendDTO;
import suza.project.suza_hr_support.service.AttendService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v2/hrsupport/attendance")
public class AttendController {
    @Autowired
    private AttendService attendService;

    @GetMapping
    public ResponseEntity<List<AttendDTO>> getAllAttendance() {
        List<AttendDTO> attendance = attendService.getAllAttendance();
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendDTO> getAttendanceById(@PathVariable Long id) {
        AttendDTO attend = attendService.getAttendanceById(id);
        return ResponseEntity.ok(attend);
    }

    @PostMapping
    public ResponseEntity<AttendDTO> addAttendance(@RequestBody AttendDTO attendDTO) {
        AttendDTO attendance = attendService.addAttendance(attendDTO);
        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<AttendDTO> editAttendance(@PathVariable Long id, @RequestBody AttendDTO attendDTO) {
        AttendDTO attend = attendService.editAttendance(id, attendDTO);
        return new ResponseEntity<>(attend, HttpStatus.OK);
    }
   
}
