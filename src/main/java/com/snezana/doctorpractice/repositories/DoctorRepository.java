package com.snezana.doctorpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snezana.doctorpractice.models.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	Doctor findByLicenceCode(String licenceCode);

}
