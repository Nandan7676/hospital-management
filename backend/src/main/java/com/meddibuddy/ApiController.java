package com.meddibuddy;
import com.meddibuddy.appointment.*;import com.meddibuddy.appointment.service.AppointmentService;import com.meddibuddy.doctor.*;import com.meddibuddy.doctor.service.DoctorService;import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import org.springframework.security.core.Authentication;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import java.time.*;import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin(origins="http://localhost:5173")
public class ApiController {
 private final AppointmentService appointments;private final DoctorService doctors;public ApiController(AppointmentService appointments,DoctorService doctors){this.appointments=appointments;this.doctors=doctors;}
 @GetMapping("/doctors") public List<DoctorDto> doctors(@RequestParam(required=false) String search,@RequestParam(required=false) String specialty){return doctors.search(search,specialty);}
 @GetMapping("/doctors/{id}") public DoctorDto doctor(@PathVariable Long id){return doctors.get(id);}
 public record Booking(@NotNull Long doctorId,@NotNull LocalDate date,@NotBlank String time,String reason){}
 @PostMapping("/appointments") public ResponseEntity<Appointment> book(@Valid @RequestBody Booking b,Authentication auth){return ResponseEntity.status(201).body(appointments.book(auth.getName(),doctors.get(b.doctorId()),b.date(),b.time(),b.reason()));}
 @GetMapping("/appointments/my") public List<Appointment> my(Authentication auth){return appointments.findMine(auth.getName());}
 @PatchMapping("/appointments/{id}/cancel") public Appointment cancel(@PathVariable Long id,Authentication auth){return appointments.cancel(id,auth.getName());}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("status","ok","application","MeddiBuddy");}
 @GetMapping("/admin/dashboard") public Map<String,Object> admin(){return Map.of("doctorCount",doctors.count(),"appointmentCount",appointments.count());}
}
