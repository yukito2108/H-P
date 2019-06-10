package com.snezana.doctorpractice.services;

import java.util.Date;
import java.util.List;

import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.Doctor;
import com.snezana.doctorpractice.models.Patient;
import com.snezana.doctorpractice.models.PatientRecord;
import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.dto.AppointmentView;
import com.snezana.doctorpractice.dto.DoctorUserDto;
import com.snezana.doctorpractice.dto.DoctorUserEdto;
import com.snezana.doctorpractice.dto.MailDto;
import com.snezana.doctorpractice.dto.PatientRecordDto;
import com.snezana.doctorpractice.dto.PatientUserDto;
import com.snezana.doctorpractice.dto.PatientUserEdto;
import com.snezana.doctorpractice.dto.UserDto;
import com.snezana.doctorpractice.dto.UserEdto;

/**
 * Service for Users and User data.
 */
public interface UserService {
	
	/**
	 * Returns list of all users.
	 * @return
	 */
	List<User> listAll();
	
	/**
	 * Retrieves {@link User} from database.
	 * @param username unique username.
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * Returns list of all administrators among users.
	 * @return
	 */
	List<User> findAllAdmins();
	
	/**
	 * Returns list of all doctors among users.
	 * @return
	 */
	List<User> findAllDoctors();
	
	/**
	 * Returns list of all patients among users.
	 * @return
	 */
	List<User> findAllPatients();
	
	/**
	 * Returns list of all valid patients among users (username != 'unknown').
	 * @return
	 */
	List<User> findAllValidPatients();
	
	/**
	 * Gives list of all medical records by particular {@link Patient}.
	 * @param patient {@link Patient} whose records we are looking for
	 * @return
	 */
	List<PatientRecord> findAllRecordsByPatient(Patient patient);
	
	/**
	 * Returns {@link User} with the given id
	 * @param userId (unique) id
	 * @return
	 */
	User findUserById(Long userId);
	
	/**
	 * Gives medical record with the given id.
	 * @param patientRecordId (unique) id
	 * @return
	 */
	PatientRecord findPatientRecordById(Long patientRecordId);
	
	/**
	 * Returns {@link Doctor} with the given id
	 * @param doctorId (unique) id
	 * @return
	 */
	Doctor findDoctorById(Long doctorId);
	
	/**
	 * Creates new {@link User} account with the given role
	 * @param userDto {@link UserDto} user DTO object
	 * @param roleType role of the user
	 */
	void registerNewUserAccount(UserDto userDto, String roleType);
	
	/**
	 * Creates new {@link User} account of a doctor {@link Doctor}
	 * @param doctorUserDto DTO object with particular doctor and user data 
	 * @param roleType role of the user
	 */
	void registerNewDoctorUserAccount(DoctorUserDto doctorUserDto, String roleType);
	
	/**
	 * Creates new {@link User} account of a patient {@link Patient}
	 * @param patientUserDto DTO object with particular patient and user data 
	 * @param roleType role of the user
	 */
	void registerNewPatientUserAccount(PatientUserDto patientUserDto, String roleType);
	
	/**
	 * Creates new {@link Doctor} object with data from {@link DoctorUserDto} object
	 * @param doctorUserDto DTO object with particular doctor and user data
	 */
	void registerNewDoctor(DoctorUserDto doctorUserDto);
	
	/**
	 * Creates new {@link Patient} object with data from {@link PatientUserDto} object
	 * @param patientUserDto DTO object with particular patient and user data
	 */
	void registerNewPatient(PatientUserDto patientUserDto);
	
	/**
	 * Applies changes for a User
	 * @param userEdto {@link UserEdto} DTO object with data to be changed
	 * @param userId (unique) id of user
	 */
	void updateUser(UserEdto userEdto, Long userId);
	
	/**
	 * Applies changes for a Doctor and associated User account
	 * @param doctorUserEdto {@link DoctorUserEdto} DTO object with data to be changed
	 * @param userId (unique) id of user
	 */
	void updateDoctorUser(DoctorUserEdto doctorUserEdto, Long userId);
	
	/**
	 * Applies changes for a Patient and associated User account
	 * @param patientUserEdto {@link PatientUserEdto} DTO object with data to be changed
	 * @param userId (unique) id of user
	 */
	void updatePatientUser(PatientUserEdto patientUserEdto, Long userId);
	
	/**
	 * Change password of a {@link User}
	 * @param user {@link User} which password should be changed  
	 * @param password new password
	 */
	void changeUserPassword(final User user, final String password);
	
	/**
	 * Creates new medical record of a patient 
	 * @param patientRecordDto {@link PatientRecordDto} DTO object with particular patient and record data
	 */
	void createNewRecord(PatientRecordDto patientRecordDto);
	
	/**
	 * Creates new appointment by doctor
	 * @param tdid datum of an appointment
	 * @param status status of an appointment
	 * @param doctor {@link Doctor} who creates new appointment
	 */
	void createNewAppointment(String tdid, String status, Doctor doctor);
	
	/**
	 * Cancels appointment by doctor
	 * @param tdid datum of an appointment
	 * @param doctor {@link Doctor} who cancels the appointment
	 */
	void cancelAppointment(String tdid, Doctor doctor);
	
	/**
	 * Reserves appointment of particular doctor by patient
	 * @param tdid datum of an appointment
	 * @param status status of an appointment
	 * @param doctor {@link Doctor} who creates the appointment
	 * @param patient {@link Patient} who reserves the appointment
	 */
	void reserveAppointment(String tdid, String status, Doctor doctor, Patient patient);
	
	/**
	 * Cancels the appointment by patient
	 * @param tdid datum of an appointment
	 * @param status status of an appointment
	 * @param doctor {@link Doctor} who creates the appointment
	 */
	void patientCancelAppointment(String tdid, String status, Doctor doctor);
	
	/**
	 * Gets the list of all appointments from tomorrow 
	 * @param doctor {@link Doctor} who creates the appointments
	 * @param date datum - today
	 * @return
	 */
	List<Appointment> getAppointmentsFromTomorrow(Doctor doctor, Date date);
	
	/**
	 * Gets the list of all AppointmentViews (appointment data for scheduler view) from tomorrow
	 * @param appointments list of {@link Appointment} objects
	 * @return
	 */
	List<AppointmentView> getAppointmentsViewFromTomorrow(List<Appointment> appointments);
	
	/**
	 * Gets the list of all free appointments and appointments reserved by particular patient
	 * @param appointments list of {@link Appointment} objects
	 * @param patient {@link Patient} that reserved appointment(s)
	 * @return
	 */
	List<AppointmentView> getAppntmViewFreeAndMineFromTomorrow(List<Appointment> appointments, Patient patient);
	
	/**
	 * Gets the list of doctors
	 * @return
	 */
	List<Doctor> doctorsList();
	
	/**
	 * Creation of {@link UserEdto} object from user data
	 * @param user {@link User} data that used for creation
	 * @return
	 */
	UserEdto userToUserEdto(User user);
	
	/**
	 * Creation of {@link DoctorUserEdto} object from user data
	 * @param userId id of particular {@link User}
	 * @return
	 */
	DoctorUserEdto doctorUserToDoctorUserEdto(Long userId);
	
	/**
	 * Creation of {@link PatientUserEdto} object from user data
	 * @param userId id of particular {@link User}
	 * @return
	 */
	PatientUserEdto patientUserToPatientUserEdto(Long userId);
	
	/**
	 * Checks if the {@link User} with particular username exists
	 * @param username (unique) username of {@link User}
	 * @return {@code true} if {@code User} with particular username exist,
     * {@code false} otherwise.
	 */
	boolean usernameExist(String username);
	
	/**
	 * Checks if the {@link Doctor} with particular licenceCode exists
	 * @param licenceCode (unique) licence code of {@link Doctor}
	 * @return {@code true} if {@code Doctor} with particular licenceCode exist,
     * {@code false} otherwise.
	 */
	boolean licenceCodeExist(String licenceCode);
	
	/**
	 * Checks if the {@link Patient} with particular hsCode exists
	 * @param hsCode (unique) health security code of {@link Patient}
	 * @return {@code true} if {@code Patient} with particular hsCode exist,
     * {@code false} otherwise.
	 */
	boolean hsCodeExist(String hsCode);		
	
	/**
	 * Checks if the old password of the user was typed correctly
	 * @param user {@link User} that password should be checked
	 * @param oldPassword old password of the {@link User}
	 * @return {@code true} if the old password was typed correctly,
     * {@code false} otherwise.
	 */
	boolean checkIfValidOldPassword(final User user, final String oldPassword);
	
	/**
	 * Mail data settings when doctor sends mail to admin
	 * @param mailDto {@link MailDto} DTO object for mail data 
	 * @param drUser {@link User} who is a doctor
	 */
	void docMailDtoSettings(MailDto mailDto, User drUser);

	/**
	 * Mail data settings when patient sends mail to admin
	 * @param mailDto {@link MailDto} DTO object for mail data 
	 * @param ptUser {@link User} who is a patient
	 */
	void patMailDtoSettings(MailDto mailDto, User ptUser);
	
	/**
	 * Data settings for patient record (medical report)
	 * @param patientRecordDto {@link PatientRecordDto} DTO object for record data
	 * @param pUser {@link User} is a patient for whom the record will be created 
	 * @param drUser {@link User} is a doctor who creates record
	 */
	void patientRecordDtoSettings(PatientRecordDto patientRecordDto, User pUser, User drUser);
	
}
