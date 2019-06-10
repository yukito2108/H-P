package com.snezana.doctorpractice.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.Doctor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	public List<Appointment> findByDoctorAndAppDatetimeAfterOrderByAppDatetime(Doctor doctor,Date appDatetime);
	
	Appointment findByDoctorAndAppDatetime(Doctor doctor,Date appDatetime);

}
