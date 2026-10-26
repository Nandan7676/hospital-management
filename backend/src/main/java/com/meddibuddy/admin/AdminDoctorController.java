package com.meddibuddy.admin;
import com.meddibuddy.doctor.*;import com.meddibuddy.doctor.service.DoctorService;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/admin/doctors") public class AdminDoctorController {
 private final DoctorService doctors;public AdminDoctorController(DoctorService doctors){this.doctors=doctors;}
 public record DoctorRequest(@NotBlank String name,@NotBlank String specialty,String experience,@PositiveOrZero int fee,String location,String image,String about){DoctorService.DoctorRequest toService(){return new DoctorService.DoctorRequest(name,specialty,experience,fee,location,image,about);}}
 @GetMapping public List<DoctorDto> list(){return doctors.search(null,null);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public DoctorDto create(@Valid @RequestBody DoctorRequest r){return doctors.create(r.toService());}
 @PutMapping("/{id}") public DoctorDto update(@PathVariable Long id,@Valid @RequestBody DoctorRequest r){return doctors.update(id,r.toService());}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){doctors.delete(id);}
}
