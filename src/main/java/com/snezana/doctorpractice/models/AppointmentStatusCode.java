package com.snezana.doctorpractice.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_status_code", catalog = "doctor_practice")
public class AppointmentStatusCode {

	@Id
	@Column(name = "app_status_code", unique = true, nullable = false)
	private String appStatusCode;
	
	@Column(name = "app_status_description", unique = true, nullable = false)
	private String appStatusDescription;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appointmentStatusCode")
	private Set<Appointment> appointments;

	public String getAppStatusCode() {
		return appStatusCode;
	}

	public void setAppStatusCode(String appStatusCode) {
		this.appStatusCode = appStatusCode;
	}

	public String getAppStatusDescription() {
		return appStatusDescription;
	}

	public void setAppStatusDescription(String appStatusDescription) {
		this.appStatusDescription = appStatusDescription;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appStatusCode == null) ? 0 : appStatusCode.hashCode());
		result = prime * result + ((appStatusDescription == null) ? 0 : appStatusDescription.hashCode());
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
		AppointmentStatusCode other = (AppointmentStatusCode) obj;
		if (appStatusCode == null) {
			if (other.appStatusCode != null)
				return false;
		} else if (!appStatusCode.equals(other.appStatusCode))
			return false;
		if (appStatusDescription == null) {
			if (other.appStatusDescription != null)
				return false;
		} else if (!appStatusDescription.equals(other.appStatusDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppointmentStatusCode [appStatusCode=" + appStatusCode + ", appStatusDescription="
				+ appStatusDescription + "]";
	}	
	
}
