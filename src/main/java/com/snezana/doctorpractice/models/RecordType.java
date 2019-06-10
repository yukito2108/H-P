package com.snezana.doctorpractice.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "record_type", catalog = "doctor_practice")
public class RecordType {
	
	@Id
	@Column(name = "component_code", unique = true, nullable = false)
	private String componentCode;
	
	@Column(name = "component_description", unique = true, nullable = false)
	private String componentDescription;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recordType")
	private Set<PatientRecord> patientRecords;

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	public String getComponentDescription() {
		return componentDescription;
	}

	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}

	public Set<PatientRecord> getPatientRecords() {
		return patientRecords;
	}

	public void setPatientRecords(Set<PatientRecord> patientRecords) {
		this.patientRecords = patientRecords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentCode == null) ? 0 : componentCode.hashCode());
		result = prime * result + ((componentDescription == null) ? 0 : componentDescription.hashCode());
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
		RecordType other = (RecordType) obj;
		if (componentCode == null) {
			if (other.componentCode != null)
				return false;
		} else if (!componentCode.equals(other.componentCode))
			return false;
		if (componentDescription == null) {
			if (other.componentDescription != null)
				return false;
		} else if (!componentDescription.equals(other.componentDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RecordType [componentCode=" + componentCode + ", componentDescription=" + componentDescription + "]";
	}	
	
}
