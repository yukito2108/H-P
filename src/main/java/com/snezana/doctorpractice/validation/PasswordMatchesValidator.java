package com.snezana.doctorpractice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.dto.ChangePasswordDto;
import com.snezana.doctorpractice.dto.DoctorUserDto;
import com.snezana.doctorpractice.dto.PatientUserDto;
import com.snezana.doctorpractice.dto.UserDto;

/**
 * Password matching validator class that enforces the rules used for validation
 * during the password matching
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	private String firstFieldName;
	private String secondFieldName;
	private String errorMessageName;

	@Override
	public void initialize(final PasswordMatches constraintAnnotation) {

		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		errorMessageName = constraintAnnotation.message();
	}

	/**
	 * Method where validation rules for password matching are defined
	 */
	@Override
	public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
		boolean toReturn = false;

		if (obj instanceof UserDto) {
			final UserDto userDto = (UserDto) obj;
			toReturn = userDto.getPassword().equals(userDto.getConfirmPassword());
		} else if (obj instanceof DoctorUserDto) {
			final DoctorUserDto doctorUserDto = (DoctorUserDto) obj;
			toReturn = doctorUserDto.getPassword().equals(doctorUserDto.getConfirmPassword());
		} else if (obj instanceof PatientUserDto) {
			final PatientUserDto patientUserDto = (PatientUserDto) obj;
			toReturn = patientUserDto.getPassword().equals(patientUserDto.getConfirmPassword());
		} else {
			final ChangePasswordDto chPasswDto = (ChangePasswordDto) obj;
			toReturn = chPasswDto.getPassword().equals(chPasswDto.getConfirmPassword());
		}
		if (!toReturn) {
			context.disableDefaultConstraintViolation();
			// In the initialiaze method you get the errorMessage:
			// constraintAnnotation.message();
			context.buildConstraintViolationWithTemplate(errorMessageName).addPropertyNode(secondFieldName)
					.addConstraintViolation();

		}
		return toReturn;
	}
}
