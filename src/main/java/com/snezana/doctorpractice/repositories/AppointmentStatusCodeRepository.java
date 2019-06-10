package com.snezana.doctorpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.snezana.doctorpractice.models.AppointmentStatusCode;

public interface AppointmentStatusCodeRepository extends JpaRepository<AppointmentStatusCode, Long>{

	AppointmentStatusCode findByAppStatusCode(String appStatusCode);
}
