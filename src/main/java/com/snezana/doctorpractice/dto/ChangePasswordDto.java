package com.snezana.doctorpractice.dto;

import com.snezana.doctorpractice.validation.PasswordMatches;
import com.snezana.doctorpractice.validation.ValidPassword;

/**
 * DTO (Data Transfer Object) class format for password changing (with
 * annotations used for field validation)
 */
@PasswordMatches(first = "password", second = "confirmPassword", message = "Passwords must match!")
public class ChangePasswordDto {

	private String oldPassword;

	@ValidPassword
	private String password;

	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((oldPassword == null) ? 0 : oldPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		ChangePasswordDto other = (ChangePasswordDto) obj;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (oldPassword == null) {
			if (other.oldPassword != null)
				return false;
		} else if (!oldPassword.equals(other.oldPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChangePasswordDto [oldPassword=" + oldPassword + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
