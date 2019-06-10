package com.snezana.doctorpractice.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment", catalog = "doctor_practice")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "app_id", unique = true, nullable = false)
	private Long appointmId;
	
	@Column(name = "app_datetime", nullable = false)
	private Date appDatetime;
	
	@Column(name = "app_end_datetime", nullable = false)
	private Date appEndDatetime;
	
	@Column(name = "appointment_details", nullable = false)
	private String appointmentDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_status_code")
	private AppointmentStatusCode appointmentStatusCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;
		

	public Long getAppointmId() {
		return appointmId;
	}

	public void setAppointmId(Long appointmId) {
		this.appointmId = appointmId;
	}

	public Date getAppDatetime() {
		return appDatetime;
	}

	public void setAppDatetime(Date appDatetime) {
		this.appDatetime = appDatetime;
	}

	public Date getAppEndDatetime() {
		return appEndDatetime;
	}

	public void setAppEndDatetime(Date appEndDatetime) {
		this.appEndDatetime = appEndDatetime;
	}

	public String getAppointmentDetails() {
		return appointmentDetails;
	}

	public void setAppointmentDetails(String appointmentDetails) {
		this.appointmentDetails = appointmentDetails;
	}

	public AppointmentStatusCode getAppointmentStatusCode() {
		return appointmentStatusCode;
	}

	public void setAppointmentStatusCode(AppointmentStatusCode appointmentStatusCode) {
		this.appointmentStatusCode = appointmentStatusCode;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appDatetime == null) ? 0 : appDatetime.hashCode());
		result = prime * result + ((appEndDatetime == null) ? 0 : appEndDatetime.hashCode());
		result = prime * result + ((appointmId == null) ? 0 : appointmId.hashCode());
		result = prime * result + ((appointmentDetails == null) ? 0 : appointmentDetails.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (appDatetime == null) {
			if (other.appDatetime != null)
				return false;
		} else if (!appDatetime.equals(other.appDatetime))
			return false;
		if (appEndDatetime == null) {
			if (other.appEndDatetime != null)
				return false;
		} else if (!appEndDatetime.equals(other.appEndDatetime))
			return false;
		if (appointmId == null) {
			if (other.appointmId != null)
				return false;
		} else if (!appointmId.equals(other.appointmId))
			return false;
		if (appointmentDetails == null) {
			if (other.appointmentDetails != null)
				return false;
		} else if (!appointmentDetails.equals(other.appointmentDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Appointment [appointmId=" + appointmId + ", appDatetime=" + appDatetime + ", appEndDatetime="
				+ appEndDatetime + ", appointmentDetails=" + appointmentDetails + "]";
	}
	

}
