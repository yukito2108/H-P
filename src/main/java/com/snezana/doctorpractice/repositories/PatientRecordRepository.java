package com.snezana.doctorpractice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snezana.doctorpractice.models.Patient;
import com.snezana.doctorpractice.models.PatientRecord;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long>{
	
	public List<PatientRecord> findByPatient(Patient patient);

}
