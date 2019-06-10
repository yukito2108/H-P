package com.snezana.doctorpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snezana.doctorpractice.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Patient findByHsCode(String hsCode);
}
