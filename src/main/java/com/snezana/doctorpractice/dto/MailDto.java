package com.snezana.doctorpractice.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Mail DTO (Data Transfer Object) class message format for sending email from a Spring
 * Boot app (with annotations used for field validation)
 */
public class MailDto {

	private Long id;

	private String userEmail;

	private String username;

	private String firstLastName;

	private String telephone;

	@NotEmpty
	private String subjectEmail;

	@NotEmpty
	private String messageEmail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectEmail() {
		return subjectEmail;
	}

	public void setSubjectEmail(String subjectEmail) {
		this.subjectEmail = subjectEmail;
	}

	public String getMessageEmail() {
		return messageEmail;
	}

	public void setMessageEmail(String messageEmail) {
		this.messageEmail = messageEmail;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstLastName == null) ? 0 : firstLastName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageEmail == null) ? 0 : messageEmail.hashCode());
		result = prime * result + ((subjectEmail == null) ? 0 : subjectEmail.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		MailDto other = (MailDto) obj;
		if (firstLastName == null) {
			if (other.firstLastName != null)
				return false;
		} else if (!firstLastName.equals(other.firstLastName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageEmail == null) {
			if (other.messageEmail != null)
				return false;
		} else if (!messageEmail.equals(other.messageEmail))
			return false;
		if (subjectEmail == null) {
			if (other.subjectEmail != null)
				return false;
		} else if (!subjectEmail.equals(other.subjectEmail))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MailDto [id=" + id + ", userEmail=" + userEmail + ", username=" + username + ", firstLastName="
				+ firstLastName + ", telephone=" + telephone + ", subjectEmail=" + subjectEmail + ", messageEmail="
				+ messageEmail + "]";
	}

}
