package com.meddibuddy.appointment;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{List<Appointment> findByPatientEmailOrderByAppointmentDateDesc(String email);boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(Long doctorId,java.time.LocalDate date,String time,String status);}
