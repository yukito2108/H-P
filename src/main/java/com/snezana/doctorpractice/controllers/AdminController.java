package com.snezana.doctorpractice.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.snezana.doctorpractice.dto.AppointmentView;
import com.snezana.doctorpractice.dto.ChangePasswordDto;
import com.snezana.doctorpractice.dto.DoctorUserDto;
import com.snezana.doctorpractice.dto.DoctorUserEdto;
import com.snezana.doctorpractice.dto.PatientUserDto;
import com.snezana.doctorpractice.dto.PatientUserEdto;
import com.snezana.doctorpractice.dto.UserDto;
import com.snezana.doctorpractice.dto.UserEdto;
import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.Doctor;
import com.snezana.doctorpractice.models.PatientRecord;
import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.services.UserService;
import com.snezana.doctorpractice.utils.PdfGeneratorUtil;
import com.snezana.doctorpractice.utils.SomeUtils;

/**
 * Controller class for administrator activities.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PdfGeneratorUtil pdfGeneratorUtil;

	/**
	 * Page that gives the list of all admins
	 */
	@RequestMapping(value = "/usersAdm", method = RequestMethod.GET)
	public String alladm(ModelMap model) {
		model.addAttribute("allAdmins", userService.findAllAdmins());
		return "usersAdm";
	}

	/**
	 * Add new admin page
	 */
	@RequestMapping(value = "/addAdm", method = RequestMethod.GET)
	public String addAdm(ModelMap model) {
		model.addAttribute("userDto", new UserDto());
		return "addAdm";
	}

	/**
	 * Add new admin activity with data validation
	 */
	@RequestMapping(value = "/addAdm", method = RequestMethod.POST)
	public String adminadd(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult,
			ModelMap model, @RequestParam(value = "roleType", required = true) String roleType) {
		String formMessage = "";		
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "addAdm";
		} else {
			if (!userService.usernameExist(userDto.getUsername())) {
				userService.registerNewUserAccount(userDto, roleType);
				formMessage = "Form successfully completed and User: " + userDto.getUsername() + " is added to DB!";
				model.addAttribute("formMessageScc", formMessage);
			} else {

				formMessage = "There is an account with that email adress: " + userDto.getUsername();
				model.addAttribute("formMessageNoUse", formMessage);
			}
			model.addAttribute("userDto", new UserDto());
			return "addAdm";
		}
	}

	/**
	 * Edit admin page
	 */
	@RequestMapping(value = "/editAdm", method = RequestMethod.GET)
	public String editAdm(ModelMap model, @RequestParam(value = "id", required = true) Long id) {
		User user = userService.findUserById(id);
		model.addAttribute("userEdto", userService.userToUserEdto(user));
		return "editAdm";
	}

	/**
	 * Edit admin activity with data validation
	 */
	@RequestMapping(value = "/editAdm", method = RequestMethod.POST)
	public String adminedit(@ModelAttribute("userEdto") @Valid UserEdto userEdto, BindingResult bindingResult,
			ModelMap model) {
		String formMessage = "";
		model.addAttribute("username", userEdto.getUsername());
		model.addAttribute("email", userEdto.getEmail());
		model.addAttribute("address", userEdto.getAddress());
		model.addAttribute("telephone", userEdto.getTelephone());
		model.addAttribute("userStatus", userEdto.getUserStatus());
		model.addAttribute("userId", userEdto.getId());
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "editAdm";
		} else {
			userService.updateUser(userEdto, userEdto.getId());
			formMessage = "User: " + userEdto.getUsername() + " successfully updated in DB!";
			model.addAttribute("formMessageScc", formMessage);
			model.addAttribute("userDto", new UserDto());
			return "editAdm";
		}
	}

	/**
	 * Page that gives the list of all doctors
	 */
	@RequestMapping(value = "/usersDoc", method = RequestMethod.GET)
	public String addDoctor(ModelMap model) {
		model.addAttribute("allDoctors", userService.findAllDoctors());
		return "usersDoc";
	}

	/**
	 * Add new doctor page
	 */
	@RequestMapping(value = "/addDoc", method = RequestMethod.GET)
	public String addDoc(ModelMap model) {
		model.addAttribute("doctorUserDto", new DoctorUserDto());
		return "addDoc";
	}

	/**
	 * Add new doctor activity with data validation
	 */
	@RequestMapping(value = "/addDoc", method = RequestMethod.POST)
	public String adminDoc(@ModelAttribute("doctorUserDto") @Valid DoctorUserDto doctorUserDto,
			BindingResult bindingResult, ModelMap model,
			@RequestParam(value = "roleType", required = true) String roleType) {
		String formMessage = "";		
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "addDoc";
		} else {
			if (!userService.usernameExist(doctorUserDto.getUsername())
					&& !userService.licenceCodeExist(doctorUserDto.getLicenceCode())) {
				userService.registerNewDoctorUserAccount(doctorUserDto, roleType);
				userService.registerNewDoctor(doctorUserDto);
				formMessage = "Form successfully completed and Doctor is added to DB!";
				model.addAttribute("formMessageScc", formMessage);
				model.addAttribute("doctorUserDto", new DoctorUserDto());
			} else {
				formMessage = "There is an account with that username or/and licencecode!";
				model.addAttribute("formMessageNoUse", formMessage);
			}
		}
		return "addDoc";
	}

	/**
	 * Info doctor page
	 */
	@RequestMapping(value = "/infoDoc", method = RequestMethod.GET)
	public String infoDoc(ModelMap model, @RequestParam(value = "id", required = true) Long id) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("birthDate", SomeUtils.dateView(user.getDoctor().getBirthDate()));
		return "infoDoc";
	}

	/**
	 * Edit doctor page
	 */
	@RequestMapping(value = "/editDoc", method = RequestMethod.GET)
	public String editDoc(ModelMap model, @RequestParam(value = "id", required = true) Long id) {		
		model.addAttribute("doctorUserEdto", userService.doctorUserToDoctorUserEdto(id));
		return "editDoc";
	}

	/**
	 * Edit doctor activity with data validation
	 */
	@RequestMapping(value = "/editDoc", method = RequestMethod.POST)
	public String docEdit(@ModelAttribute("doctorUserEdto") @Valid DoctorUserEdto doctorUserEdto,
			BindingResult bindingResult, ModelMap model) {
		String formMessage = "";		
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "editDoc";
		} else {
			userService.updateDoctorUser(doctorUserEdto, doctorUserEdto.getId());
			formMessage = "User: " + doctorUserEdto.getUsername() + " successfully updated in DB!";
			model.addAttribute("formMessageScc", formMessage);
			model.addAttribute("doctorUserEdto", new DoctorUserEdto());
			return "editDoc";
		}
	}

	/**
	 * Page that gives the list of all patients
	 */
	@RequestMapping(value = "/usersPtn", method = RequestMethod.GET)
	public String addPatient(ModelMap model) {
		model.addAttribute("allPatients", userService.findAllValidPatients());
		return "usersPtn";
	}

	/**
	 * Add new patient page
	 */
	@RequestMapping(value = "/addPtn", method = RequestMethod.GET)
	public String addPtn(ModelMap model) {
		model.addAttribute("patientUserDto", new PatientUserDto());
		return "addPtn";
	}

	/**
	 * Add new patient activity with data validation
	 */
	@RequestMapping(value = "/addPtn", method = RequestMethod.POST)
	public String adminPtn(@ModelAttribute("patientUserDto") @Valid PatientUserDto patientUserDto,
			BindingResult bindingResult, ModelMap model,
			@RequestParam(value = "roleType", required = true) String roleType) {
		String formMessage = "";
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "addPtn";
		} else {
			if (!userService.usernameExist(patientUserDto.getUsername())
					&& !userService.hsCodeExist(patientUserDto.getHsCode())) {
				userService.registerNewPatientUserAccount(patientUserDto, roleType);
				userService.registerNewPatient(patientUserDto);
				formMessage = "Form successfully completed and Patient is added to DB!";
				model.addAttribute("formMessageScc", formMessage);
				model.addAttribute("patientUserDto", new PatientUserDto());
			} else {
				formMessage = "There is an account with that username or/and licencecode!";
				model.addAttribute("formMessageNoUse", formMessage);
			}
		}
		return "addPtn";
	}

	/**
	 * Info patient page 
	 */
	@RequestMapping(value = "/infoPtn", method = RequestMethod.GET)
	public String infoPtn(ModelMap model, @RequestParam(value = "id", required = true) Long id) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("birthDate", SomeUtils.dateView(user.getPatient().getBirthDate()));
		return "infoPtn";
	}

	/**
	 * Edit patient page
	 */
	@RequestMapping(value = "/editPtn", method = RequestMethod.GET)
	public String editPtn(ModelMap model, @RequestParam(value = "id", required = true) Long id) {
		model.addAttribute("patientUserEdto", userService.patientUserToPatientUserEdto(id));
		return "editPtn";
	}

	/**
	 * Edit patient activity with data validation
	 */
	@RequestMapping(value = "/editPtn", method = RequestMethod.POST)
	public String ptn1edit(@ModelAttribute("patientUserEdto") @Valid PatientUserEdto patientUserEdto,
			BindingResult bindingResult, ModelMap model) {
		String formMessage = "";

		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "editPtn";
		} else {
			userService.updatePatientUser(patientUserEdto, patientUserEdto.getId());
			formMessage = "User: " + patientUserEdto.getUsername() + " successfully updated in DB!";
			model.addAttribute("formMessageScc", formMessage);
			model.addAttribute("patientUserEdto", new PatientUserEdto());
			return "editPtn";
		}
	}

	/**
	 * Change admin password page
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(ModelMap model) {
		model.addAttribute("changePasswordDto", new ChangePasswordDto());
		return "changePassword";
	}

	/**
	 * Action for checking old password and changing it by new one with data validation
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword2(@ModelAttribute("changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
			BindingResult bindingResult, ModelMap model, Principal principal) {
		String username = principal.getName();
		User user = userService.findByUsername(username);
		if (bindingResult.hasErrors()) {
			return "changePassword";
		}
		if (!userService.checkIfValidOldPassword(user, changePasswordDto.getOldPassword())) {
			return "redirect:/admin/changePassword?err";
		}
		userService.changeUserPassword(user, changePasswordDto.getPassword());
		return "redirect:/admin/changePassword?succ";
	}	
	
	/**
	 * List of all patients which records should be viewed
	 */
	@RequestMapping(value = "/admPatRec", method = RequestMethod.GET)
	public String admPatRec(ModelMap model) {
		model.addAttribute("allPatients", userService.findAllValidPatients());
		return "admPatRec";
	}	
	
	/**
	 * List of all doctors which scheduler and appointment list should be viewed
	 */
	@RequestMapping(value = "/admAppnmt", method = RequestMethod.GET)
	public String admAppnmt(ModelMap model) {
		model.addAttribute("allDoctors", userService.doctorsList());
		return "admAppnmt";
	}
	
	/**
	 * List of all records for a patient with particular id
	 */
	@RequestMapping(value = "/admViewPrintRecords", method = RequestMethod.GET)
	public String viewPrint(ModelMap model, Principal principal, @RequestParam(value = "id", required = true) Long id) {		
		User pUser = userService.findUserById(id);
		List<PatientRecord> patientRecordsList = userService.findAllRecordsByPatient(pUser.getPatient());
		model.addAttribute("patientRecordsList", patientRecordsList);
		String patientName = pUser.getPatient().getFirstName() + " " + pUser.getPatient().getLastName();
		model.addAttribute("patientName", patientName);
		return "admViewPrintRecords";
	}
	
	/**
	 * View of one record (medical report) with particular id
	 */
	@RequestMapping(value = "/admViewPrintOneRecord", method = RequestMethod.GET)
	public String admViewPrintOneRecord(ModelMap model, Principal principal,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "formMessage", required = false) String formMessage) {
		PatientRecord patientRecord = userService.findPatientRecordById(id);		
		String date = SomeUtils.dateToString2(patientRecord.getDate());
		String birthDate = SomeUtils.dateView(patientRecord.getPatient().getBirthDate());
		model.addAttribute("ptnFirstName", patientRecord.getPatient().getFirstName());
		model.addAttribute("ptnLastName", patientRecord.getPatient().getLastName());
		model.addAttribute("date", date);
		model.addAttribute("ptnHsCode", patientRecord.getPatient().getHsCode());
		model.addAttribute("ptnBirthDate", birthDate);
		model.addAttribute("ptnGender", patientRecord.getPatient().getGender());
		model.addAttribute("componentCode", patientRecord.getRecordType().getComponentCode());
		model.addAttribute("recordDetails", patientRecord.getRecordDetails());
		model.addAttribute("docQualifications", patientRecord.getDoctor().getQualifications());
		model.addAttribute("docLicenceCode", patientRecord.getDoctor().getLicenceCode());
		model.addAttribute("docFirstName", patientRecord.getDoctor().getFirstName());
		model.addAttribute("docLastName", patientRecord.getDoctor().getLastName());
		model.addAttribute("patientRecordId", id);
		model.addAttribute("formMessage", formMessage);
		return "admViewPrintOneRecord";
	}
	
	/**
	 * Print to PDF action of one medical report with particular id 
	 */
	@RequestMapping(value = "/admViewPrintOneRecordToPDF", method = RequestMethod.GET)
	public String admViewPrintOneRecord(@RequestParam(value = "id", required = true) Long id, ModelMap model,
			RedirectAttributes redirectAttrs) {
		String formMessage = "";
		PatientRecord patientRecord = userService.findPatientRecordById(id);
		Map<String, String> data = new HashMap<String, String>();
		String date = SomeUtils.dateToString2(patientRecord.getDate());
		String birthDate = SomeUtils.dateView(patientRecord.getPatient().getBirthDate());
		data.put("ptnFirstName", patientRecord.getPatient().getFirstName());
		data.put("ptnLastName", patientRecord.getPatient().getLastName());		
		data.put("date", date);
		data.put("ptnHsCode", patientRecord.getPatient().getHsCode());
		data.put("ptnBirthDate", birthDate);
		data.put("ptnGender", patientRecord.getPatient().getGender());
		data.put("componentCode", patientRecord.getRecordType().getComponentCode());
		data.put("recordDetails", patientRecord.getRecordDetails());
		data.put("docQualifications", patientRecord.getDoctor().getQualifications());
		data.put("docLicenceCode", patientRecord.getDoctor().getLicenceCode());
		data.put("docFirstName", patientRecord.getDoctor().getFirstName());
		data.put("docLastName", patientRecord.getDoctor().getLastName());
		try {
			pdfGeneratorUtil.createPdf("pdfReportTempl", data);
			formMessage = "Created to PDF!";			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			formMessage = "Failed!";			
			e.printStackTrace();
		}		
		redirectAttrs.addAttribute("id", id);
		redirectAttrs.addAttribute("formMessage", formMessage);		
		return "redirect:/admin/admViewPrintOneRecord";
	}

	/**
	 * Page that gives the appointment scheduler of a doctor with particular doctorId
	 */
	@RequestMapping(value = "/admSchdl", method = RequestMethod.GET)
	public String admSchdl(ModelMap model, @RequestParam(value = "doctorId", required = true) Long doctorId) {
		Date date = SomeUtils.today();
		Doctor doctor = userService.findDoctorById(doctorId);
		List<Appointment> listAppointm = userService.getAppointmentsFromTomorrow(doctor, date);
		model.addAttribute("listAppointm", listAppointm);
		List<AppointmentView> appViewList = userService.getAppointmentsViewFromTomorrow(listAppointm);
		model.addAttribute("appViewList", appViewList);
		String doctorName = doctor.getFirstName() + " " +doctor.getLastName();
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("dctrId", doctor.getDoctorId());
		SomeUtils.setHourStrings(model);
		return "admSchdl";
	}	

	/**
	 * Chat Corner page
	 */
	@RequestMapping(value = "/admChat", method = RequestMethod.GET)
	public String admChat(ModelMap model, Principal principal) {	
		String username = principal.getName();		
		model.addAttribute("username", username);
		return "admChat";
	}	
	
	/**
	 * Page that gives the appointment list of a doctor with particular doctorId
	 */
	@RequestMapping(value = "/admAppnmtLst", method = RequestMethod.GET)
	public String admAppnmtLst(ModelMap model, Principal principal, @RequestParam(value = "doctorId", required = true) Long doctorId) {
		Doctor doctor = userService.findDoctorById(doctorId);
		Date date = SomeUtils.today();
		List<Appointment> listAppointm = userService.getAppointmentsFromTomorrow(doctor, date);
		model.addAttribute("listaAppntm", listAppointm);
		String doctorName = doctor.getFirstName() + " " +doctor.getLastName();
		model.addAttribute("doctorName", doctorName);
		return "admAppnmtLst";
	}	
	
}
