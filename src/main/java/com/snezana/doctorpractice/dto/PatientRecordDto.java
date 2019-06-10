package com.snezana.doctorpractice.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.snezana.doctorpractice.models.RecordType;

/**
 * PatientRecord DTO (Data Transfer Object) class format for medical report data
 * creation (with annotations used for field validation)
 */
public class PatientRecordDto {

	private Long id;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date date;

	@NotEmpty
	private String recordDetails;

	@NotNull
	@Pattern(regexp = "^(?=\\s*\\S).*$", message = "Please, select a record type!")
	private String componentCode;

	private String ptnFirstName;

	private String ptnLastName;

	private String ptnBirthDate;

	private String ptnGender;

	private String ptnHsCode;

	private String docFirstName;

	private String docLastName;

	private String docQualifications;

	private String docLicenceCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	public String getPtnFirstName() {
		return ptnFirstName;
	}

	public void setPtnFirstName(String ptnFirstName) {
		this.ptnFirstName = ptnFirstName;
	}

	public String getPtnLastName() {
		return ptnLastName;
	}

	public void setPtnLastName(String ptnLastName) {
		this.ptnLastName = ptnLastName;
	}

	public String getPtnBirthDate() {
		return ptnBirthDate;
	}

	public void setPtnBirthDate(String ptnBirthDate) {
		this.ptnBirthDate = ptnBirthDate;
	}

	public String getPtnGender() {
		return ptnGender;
	}

	public void setPtnGender(String ptnGender) {
		this.ptnGender = ptnGender;
	}

	public String getPtnHsCode() {
		return ptnHsCode;
	}

	public void setPtnHsCode(String ptnHsCode) {
		this.ptnHsCode = ptnHsCode;
	}

	public String getDocFirstName() {
		return docFirstName;
	}

	public void setDocFirstName(String docFirstName) {
		this.docFirstName = docFirstName;
	}

	public String getDocLastName() {
		return docLastName;
	}

	public void setDocLastName(String docLastName) {
		this.docLastName = docLastName;
	}

	public String getDocQualifications() {
		return docQualifications;
	}

	public void setDocQualifications(String docQualifications) {
		this.docQualifications = docQualifications;
	}

	public String getDocLicenceCode() {
		return docLicenceCode;
	}

	public void setDocLicenceCode(String docLicenceCode) {
		this.docLicenceCode = docLicenceCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentCode == null) ? 0 : componentCode.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((docFirstName == null) ? 0 : docFirstName.hashCode());
		result = prime * result + ((docLastName == null) ? 0 : docLastName.hashCode());
		result = prime * result + ((docLicenceCode == null) ? 0 : docLicenceCode.hashCode());
		result = prime * result + ((docQualifications == null) ? 0 : docQualifications.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ptnBirthDate == null) ? 0 : ptnBirthDate.hashCode());
		result = prime * result + ((ptnFirstName == null) ? 0 : ptnFirstName.hashCode());
		result = prime * result + ((ptnGender == null) ? 0 : ptnGender.hashCode());
		result = prime * result + ((ptnHsCode == null) ? 0 : ptnHsCode.hashCode());
		result = prime * result + ((ptnLastName == null) ? 0 : ptnLastName.hashCode());
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
		PatientRecordDto other = (PatientRecordDto) obj;
		if (componentCode == null) {
			if (other.componentCode != null)
				return false;
		} else if (!componentCode.equals(other.componentCode))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (docFirstName == null) {
			if (other.docFirstName != null)
				return false;
		} else if (!docFirstName.equals(other.docFirstName))
			return false;
		if (docLastName == null) {
			if (other.docLastName != null)
				return false;
		} else if (!docLastName.equals(other.docLastName))
			return false;
		if (docLicenceCode == null) {
			if (other.docLicenceCode != null)
				return false;
		} else if (!docLicenceCode.equals(other.docLicenceCode))
			return false;
		if (docQualifications == null) {
			if (other.docQualifications != null)
				return false;
		} else if (!docQualifications.equals(other.docQualifications))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ptnBirthDate == null) {
			if (other.ptnBirthDate != null)
				return false;
		} else if (!ptnBirthDate.equals(other.ptnBirthDate))
			return false;
		if (ptnFirstName == null) {
			if (other.ptnFirstName != null)
				return false;
		} else if (!ptnFirstName.equals(other.ptnFirstName))
			return false;
		if (ptnGender == null) {
			if (other.ptnGender != null)
				return false;
		} else if (!ptnGender.equals(other.ptnGender))
			return false;
		if (ptnHsCode == null) {
			if (other.ptnHsCode != null)
				return false;
		} else if (!ptnHsCode.equals(other.ptnHsCode))
			return false;
		if (ptnLastName == null) {
			if (other.ptnLastName != null)
				return false;
		} else if (!ptnLastName.equals(other.ptnLastName))
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
		return "PatientRecordDto [id=" + id + ", date=" + date + ", recordDetails=" + recordDetails + ", componentCode="
				+ componentCode + ", ptnFirstName=" + ptnFirstName + ", ptnLastName=" + ptnLastName + ", ptnBirthDate="
				+ ptnBirthDate + ", ptnGender=" + ptnGender + ", ptnHsCode=" + ptnHsCode + ", docFirstName="
				+ docFirstName + ", docLastName=" + docLastName + ", docQualifications=" + docQualifications
				+ ", docLicenceCode=" + docLicenceCode + "]";
	}

}
