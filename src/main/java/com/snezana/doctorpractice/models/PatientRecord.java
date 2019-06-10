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
@Table(name = "patient_record", catalog = "doctor_practice")
public class PatientRecord {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "patient_record_id", unique = true, nullable = false)
	private Long patientRecordId;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "record_details", nullable = false)
	private String recordDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_code")
	private RecordType recordType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	public Long getPatientRecordId() {
		return patientRecordId;
	}

	public void setPatientRecordId(Long patientRecordId) {
		this.patientRecordId = patientRecordId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRecordDetails() {
		return recordDetails;
	}

	public void setRecordDetails(String recordDetails) {
		this.recordDetails = recordDetails;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((patientRecordId == null) ? 0 : patientRecordId.hashCode());
		result = prime * result + ((recordDetails == null) ? 0 : recordDetails.hashCode());
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
		PatientRecord other = (PatientRecord) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (patientRecordId == null) {
			if (other.patientRecordId != null)
				return false;
		} else if (!patientRecordId.equals(other.patientRecordId))
			return false;
		if (recordDetails == null) {
			if (other.recordDetails != null)
				return false;
		} else if (!recordDetails.equals(other.recordDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatientRecord [patientRecordId=" + patientRecordId + ", date=" + date + ", recordDetails="
				+ recordDetails + "]";
	}

}
