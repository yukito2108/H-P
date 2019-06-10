package com.snezana.doctorpractice.dto;

/**
 * Appointment data format used for retrieving stored data into scheduler view
 */
public class AppointmentView {

	private Long id;

	private String appDatetime;

	private String appStatusCode;

	private Long doctorId;

	private Long patientId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppDatetime() {
		return appDatetime;
	}

	public void setAppDatetime(String appDatetime) {
		this.appDatetime = appDatetime;
	}

	public String getAppStatusCode() {
		return appStatusCode;
	}

	public void setAppStatusCode(String appStatusCode) {
		this.appStatusCode = appStatusCode;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appDatetime == null) ? 0 : appDatetime.hashCode());
		result = prime * result + ((appStatusCode == null) ? 0 : appStatusCode.hashCode());
		result = prime * result + ((doctorId == null) ? 0 : doctorId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
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
		AppointmentView other = (AppointmentView) obj;
		if (appDatetime == null) {
			if (other.appDatetime != null)
				return false;
		} else if (!appDatetime.equals(other.appDatetime))
			return false;
		if (appStatusCode == null) {
			if (other.appStatusCode != null)
				return false;
		} else if (!appStatusCode.equals(other.appStatusCode))
			return false;
		if (doctorId == null) {
			if (other.doctorId != null)
				return false;
		} else if (!doctorId.equals(other.doctorId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppointmentView [id=" + id + ", appDatetime=" + appDatetime + ", appStatusCode=" + appStatusCode
				+ ", doctorId=" + doctorId + ", patientId=" + patientId + "]";
	}

}
