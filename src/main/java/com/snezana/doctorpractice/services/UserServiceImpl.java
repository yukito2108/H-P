package com.snezana.doctorpractice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.AppointmentStatusCode;
import com.snezana.doctorpractice.models.Doctor;
import com.snezana.doctorpractice.models.Patient;
import com.snezana.doctorpractice.models.PatientRecord;
import com.snezana.doctorpractice.models.RecordType;
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
import com.snezana.doctorpractice.repositories.AppointmentRepository;
import com.snezana.doctorpractice.repositories.AppointmentStatusCodeRepository;
import com.snezana.doctorpractice.repositories.DoctorRepository;
import com.snezana.doctorpractice.repositories.PatientRecordRepository;
import com.snezana.doctorpractice.repositories.PatientRepository;
import com.snezana.doctorpractice.repositories.RecordTypeRepository;
import com.snezana.doctorpractice.repositories.RoleRepository;
import com.snezana.doctorpractice.repositories.UserRepository;
import com.snezana.doctorpractice.utils.SomeUtils;

/**
 * @see UserService
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private RecordTypeRepository recordTypeRepository;

	@Autowired
	private PatientRecordRepository patientRecordRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AppointmentStatusCodeRepository appointmentStatusCodeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAllAdmins() {
		return userRepository.findAllAdmins();
	}

	@Override
	public void registerNewUserAccount(UserDto userDto, String roleType) {
		User nu = new User();
		nu.setUsername(userDto.getUsername());
		nu.setPassword(passwordEncoder.encode(userDto.getPassword()));
		nu.setEmail(userDto.getEmail());
		nu.setAddress(userDto.getAddress());
		nu.setTelephone(userDto.getTelephone());
		nu.setUserStatus(userDto.getUserStatus());
		nu.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRoleType(roleType))));
		userRepository.save(nu);
	}

	@Override
	public void registerNewDoctorUserAccount(DoctorUserDto doctorUserDto, String roleType) {
		User nu = new User();
		nu.setUsername(doctorUserDto.getUsername());
		nu.setPassword(passwordEncoder.encode(doctorUserDto.getPassword()));
		nu.setEmail(doctorUserDto.getEmail());
		nu.setAddress(doctorUserDto.getAddress());
		nu.setTelephone(doctorUserDto.getTelephone());
		nu.setUserStatus(doctorUserDto.getUserStatus());
		nu.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRoleType(roleType))));
		userRepository.save(nu);
	}

	@Override
	public void registerNewPatientUserAccount(PatientUserDto patientUserDto, String roleType) {
		User nu = new User();
		nu.setUsername(patientUserDto.getUsername());
		nu.setPassword(passwordEncoder.encode(patientUserDto.getPassword()));
		nu.setEmail(patientUserDto.getEmail());
		nu.setAddress(patientUserDto.getAddress());
		nu.setTelephone(patientUserDto.getTelephone());
		nu.setUserStatus(patientUserDto.getUserStatus());
		nu.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRoleType(roleType))));
		userRepository.save(nu);
	}

	@Override
	public boolean usernameExist(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void updateUser(UserEdto userEdto, Long userId) {
		User pu = userRepository.getOne(userId);
		pu.setEmail(userEdto.getEmail());
		pu.setAddress(userEdto.getAddress());
		pu.setTelephone(userEdto.getTelephone());
		pu.setUserStatus(userEdto.getUserStatus());
	}

	@Override
	public User findUserById(Long userId) {
		return userRepository.getOne(userId);
	}

	@Override
	public UserEdto userToUserEdto(User user) {
		UserEdto nedto = new UserEdto();
		nedto.setId(user.getUserId());
		nedto.setUsername(user.getUsername());
		nedto.setEmail(user.getEmail());
		nedto.setAddress(user.getAddress());
		nedto.setTelephone(user.getTelephone());
		nedto.setUserStatus(user.getUserStatus());
		return nedto;
	}

	@Override
	public List<User> findAllDoctors() {
		return userRepository.findAllDoctors();
	}

	@Override
	public List<User> findAllPatients() {
		return userRepository.findAllPatients();
	}

	@Override
	public boolean licenceCodeExist(String licenceCode) {
		Doctor doctor = doctorRepository.findByLicenceCode(licenceCode);
		if (doctor != null) {
			return true;
		}
		return false;
	}

	@Override
	public void registerNewDoctor(DoctorUserDto doctorUserDto) {
		Doctor doc = new Doctor();
		doc.setFirstName(doctorUserDto.getFirstName());
		doc.setLastName(doctorUserDto.getLastName());
		doc.setQualifications(doctorUserDto.getQualifications());
		doc.setLicenceCode(doctorUserDto.getLicenceCode());
		doc.setGender(doctorUserDto.getGender());
		doc.setBirthDate(doctorUserDto.getBirthDate());
		doc.setOther(doctorUserDto.getOther());
		User user = userRepository.findByUsername(doctorUserDto.getUsername());
		if (user != null) {
			doc.setUser(user);
		}
		doctorRepository.save(doc);
	}

	@Override
	public void registerNewPatient(PatientUserDto patientUserDto) {
		Patient ptn = new Patient();
		ptn.setFirstName(patientUserDto.getFirstName());
		ptn.setLastName(patientUserDto.getLastName());
		ptn.setHsCode(patientUserDto.getHsCode());
		ptn.setGender(patientUserDto.getGender());
		ptn.setBirthDate(patientUserDto.getBirthDate());
		ptn.setOther(patientUserDto.getOther());
		User user = userRepository.findByUsername(patientUserDto.getUsername());
		if (user != null) {
			ptn.setUser(user);
		}
		patientRepository.save(ptn);
	}

	@Override
	public void updateDoctorUser(DoctorUserEdto doctorUserEdto, Long userId) {
		User user = userRepository.getOne(userId);
		Doctor doctor = doctorRepository.getOne(user.getDoctor().getDoctorId());
		user.setEmail(doctorUserEdto.getEmail());
		user.setAddress(doctorUserEdto.getAddress());
		user.setTelephone(doctorUserEdto.getTelephone());
		user.setUserStatus(doctorUserEdto.getUserStatus());
		doctor.setFirstName(doctorUserEdto.getFirstName());
		doctor.setLastName(doctorUserEdto.getLastName());
		doctor.setQualifications(doctorUserEdto.getQualifications());
		doctor.setGender(doctorUserEdto.getGender());
		doctor.setOther(doctorUserEdto.getOther());
		doctor.setBirthDate(doctorUserEdto.getBirthDate());
	}

	@Override
	public DoctorUserEdto doctorUserToDoctorUserEdto(Long userId) {
		DoctorUserEdto doctorUserEdto = new DoctorUserEdto();
		User user = userRepository.findOne(userId);
		doctorUserEdto.setId(userId);
		doctorUserEdto.setUsername(user.getUsername());
		doctorUserEdto.setEmail(user.getEmail());
		doctorUserEdto.setAddress(user.getAddress());
		doctorUserEdto.setTelephone(user.getTelephone());
		doctorUserEdto.setUserStatus(user.getUserStatus());
		doctorUserEdto.setFirstName(user.getDoctor().getFirstName());
		doctorUserEdto.setLastName(user.getDoctor().getLastName());
		doctorUserEdto.setQualifications(user.getDoctor().getQualifications());
		doctorUserEdto.setLicenceCode(user.getDoctor().getLicenceCode());
		doctorUserEdto.setGender(user.getDoctor().getGender());
		doctorUserEdto.setBirthDate(user.getDoctor().getBirthDate());
		doctorUserEdto.setOther(user.getDoctor().getOther());
		return doctorUserEdto;
	}

	@Override
	public boolean hsCodeExist(String hsCode) {
		Patient patient = patientRepository.findByHsCode(hsCode);
		if (patient != null) {
			return true;
		}
		return false;
	}

	@Override
	public PatientUserEdto patientUserToPatientUserEdto(Long userId) {
		PatientUserEdto patientUserEdto = new PatientUserEdto();
		User user = userRepository.findOne(userId);
		patientUserEdto.setId(userId);
		patientUserEdto.setUsername(user.getUsername());
		patientUserEdto.setEmail(user.getEmail());
		patientUserEdto.setAddress(user.getAddress());
		patientUserEdto.setTelephone(user.getTelephone());
		patientUserEdto.setUserStatus(user.getUserStatus());
		patientUserEdto.setFirstName(user.getPatient().getFirstName());
		patientUserEdto.setLastName(user.getPatient().getLastName());
		patientUserEdto.setHsCode(user.getPatient().getHsCode());
		patientUserEdto.setGender(user.getPatient().getGender());
		patientUserEdto.setBirthDate(user.getPatient().getBirthDate());
		patientUserEdto.setOther(user.getPatient().getOther());
		return patientUserEdto;
	}

	@Override
	public void updatePatientUser(PatientUserEdto patientUserEdto, Long userId) {
		User user = userRepository.getOne(userId);
		Patient patient = patientRepository.getOne(user.getPatient().getPatientId());
		user.setEmail(patientUserEdto.getEmail());
		user.setAddress(patientUserEdto.getAddress());
		user.setTelephone(patientUserEdto.getTelephone());
		user.setUserStatus(patientUserEdto.getUserStatus());
		patient.setFirstName(patientUserEdto.getFirstName());
		patient.setLastName(patientUserEdto.getLastName());
		patient.setGender(patientUserEdto.getGender());
		patient.setOther(patientUserEdto.getOther());
		patient.setBirthDate(patientUserEdto.getBirthDate());
	}

	@Override
	public void changeUserPassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));		
	}

	@Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void createNewRecord(PatientRecordDto patientRecordDto) {
		PatientRecord patientRecord = new PatientRecord();

		patientRecord.setDate(patientRecordDto.getDate());
		patientRecord.setRecordDetails(patientRecordDto.getRecordDetails());

		RecordType recordType = recordTypeRepository.findByComponentCode(patientRecordDto.getComponentCode());
		if (recordType != null) {
			patientRecord.setRecordType(recordType);
		}

		Doctor doctor = doctorRepository.findByLicenceCode(patientRecordDto.getDocLicenceCode());
		if (doctor != null) {
			patientRecord.setDoctor(doctor);
		}

		Patient patient = patientRepository.findByHsCode(patientRecordDto.getPtnHsCode());
		if (patient != null) {
			patientRecord.setPatient(patient);
		}

		patientRecordRepository.save(patientRecord);
	}

	@Override
	public List<Doctor> doctorsList() {
		return doctorRepository.findAll();
	}

	@Override
	public void createNewAppointment(String tdid, String status, Doctor doctor) {
		Appointment appointment = new Appointment();
		Date date = SomeUtils.stringToDate(tdid);
		appointment.setAppDatetime(date);
		Date endDatetime = SomeUtils.addMinutesToDate(45, date);
		appointment.setAppEndDatetime(endDatetime);
		appointment.setAppointmentDetails("The term is opened");
		Long unknownPatientId = 3l;
		AppointmentStatusCode appStatCode = appointmentStatusCodeRepository.findByAppStatusCode(status);
		if (appStatCode != null) {
			appointment.setAppointmentStatusCode(appStatCode);
		}
		appointment.setDoctor(doctor);
		Patient unknownPatient = patientRepository.findOne(unknownPatientId);
		if (unknownPatient != null) {
			appointment.setPatient(unknownPatient);
		}
		appointmentRepository.save(appointment);
	}

	@Override
	public void cancelAppointment(String tdid, Doctor doctor) {
		Date date = SomeUtils.stringToDate(tdid);
		Appointment appointment = appointmentRepository.findByDoctorAndAppDatetime(doctor, date);
		appointmentRepository.delete(appointment.getAppointmId());
	}

	@Override
	public List<Appointment> getAppointmentsFromTomorrow(Doctor doctor, Date date) {
		return appointmentRepository.findByDoctorAndAppDatetimeAfterOrderByAppDatetime(doctor, date);

	}

	@Override
	public List<AppointmentView> getAppointmentsViewFromTomorrow(List<Appointment> appointments) {
		List<AppointmentView> appointmentsViewList = new ArrayList<AppointmentView>();
		String dateStr;
		for (int i = 0; i < appointments.size(); i++) {
			AppointmentView appView = new AppointmentView();
			appView.setAppStatusCode(appointments.get(i).getAppointmentStatusCode().getAppStatusCode());
			appView.setDoctorId(appointments.get(i).getDoctor().getDoctorId());
			appView.setPatientId(appointments.get(i).getPatient().getPatientId());
			dateStr = SomeUtils.dateToString(appointments.get(i).getAppDatetime());
			appView.setAppDatetime(dateStr);
			appointmentsViewList.add(appView);
		}
		return appointmentsViewList;
	}

	@Override
	public Doctor findDoctorById(Long doctorId) {
		return doctorRepository.getOne(doctorId);
	}

	@Override
	public List<AppointmentView> getAppntmViewFreeAndMineFromTomorrow(List<Appointment> appointments, Patient patient) {
		List<AppointmentView> appointmentsViewList = new ArrayList<AppointmentView>();
		String dateStr;
		for (int i = 0; i < appointments.size(); i++) {
			if (appointments.get(i).getPatient().getPatientId() == 3l
					|| appointments.get(i).getPatient().getPatientId() == patient.getPatientId()) {
				AppointmentView appView = new AppointmentView();
				appView.setAppStatusCode(appointments.get(i).getAppointmentStatusCode().getAppStatusCode());
				appView.setDoctorId(appointments.get(i).getDoctor().getDoctorId());
				appView.setPatientId(appointments.get(i).getPatient().getPatientId());
				dateStr = SomeUtils.dateToString(appointments.get(i).getAppDatetime());
				appView.setAppDatetime(dateStr);
				appointmentsViewList.add(appView);
			}
		}
		return appointmentsViewList;
	}

	@Override
	public void reserveAppointment(String tdid, String status, Doctor doctor, Patient patient) {
		Date date = SomeUtils.stringToDate(tdid);
		Appointment appointment = appointmentRepository.findByDoctorAndAppDatetime(doctor, date);
		appointment.setAppointmentDetails("The term is reserved");
		AppointmentStatusCode appStatCode = appointmentStatusCodeRepository.findByAppStatusCode(status);
		if (appStatCode != null) {
			appointment.setAppointmentStatusCode(appStatCode);
		}
		appointment.setPatient(patient);
	}

	@Override
	public void patientCancelAppointment(String tdid, String status, Doctor doctor) {
		Date date = SomeUtils.stringToDate(tdid);
		Appointment appointment = appointmentRepository.findByDoctorAndAppDatetime(doctor, date);
		appointment.setAppointmentDetails("The term is opened");
		AppointmentStatusCode appStatCode = appointmentStatusCodeRepository.findByAppStatusCode(status);
		if (appStatCode != null) {
			appointment.setAppointmentStatusCode(appStatCode);
		}
		Long unknownPatientId = 3l;
		Patient unknownPatient = patientRepository.findOne(unknownPatientId);
		if (unknownPatient != null) {
			appointment.setPatient(unknownPatient);
		}
	}

	@Override
	public List<PatientRecord> findAllRecordsByPatient(Patient patient) {
		return patientRecordRepository.findByPatient(patient);
	}

	@Override
	public PatientRecord findPatientRecordById(Long patientRecordId) {
		return patientRecordRepository.getOne(patientRecordId);
	}

	@Override
	public List<User> findAllValidPatients() {
		return userRepository.findAllValidPatients();
	}

	@Override
	public void docMailDtoSettings(MailDto mailDto, User drUser) {
		String firstLastName = drUser.getDoctor().getFirstName() + " " + drUser.getDoctor().getLastName();
		mailDto.setFirstLastName(firstLastName);
		mailDto.setUserEmail(drUser.getEmail());
		mailDto.setUsername(drUser.getUsername());
		mailDto.setTelephone(drUser.getTelephone());
	}

	@Override
	public void patMailDtoSettings(MailDto mailDto, User ptUser) {		
			String firstLastName = ptUser.getPatient().getFirstName() + " " + ptUser.getPatient().getLastName();
			mailDto.setFirstLastName(firstLastName);
			mailDto.setUserEmail(ptUser.getEmail());
			mailDto.setUsername(ptUser.getUsername());
			mailDto.setTelephone(ptUser.getTelephone());				
	}

	@Override
	public void patientRecordDtoSettings(PatientRecordDto patientRecordDto, User pUser, User drUser) {
		patientRecordDto.setPtnFirstName(pUser.getPatient().getFirstName());
		patientRecordDto.setPtnLastName(pUser.getPatient().getLastName());
		patientRecordDto.setPtnHsCode(pUser.getPatient().getHsCode());
		patientRecordDto.setPtnGender(pUser.getPatient().getGender());
		patientRecordDto.setPtnBirthDate(SomeUtils.dateView(pUser.getPatient().getBirthDate()));
		patientRecordDto.setDocFirstName(drUser.getDoctor().getFirstName());
		patientRecordDto.setDocLastName(drUser.getDoctor().getLastName());
		patientRecordDto.setDocQualifications(drUser.getDoctor().getQualifications());
		patientRecordDto.setDocLicenceCode(drUser.getDoctor().getLicenceCode());
		patientRecordDto.setDate(new Date());
	}	

}
