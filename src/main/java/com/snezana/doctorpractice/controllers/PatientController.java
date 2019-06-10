package com.snezana.doctorpractice.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snezana.doctorpractice.configurations.MailComponent;
import com.snezana.doctorpractice.dto.AppointmentView;
import com.snezana.doctorpractice.dto.ChangePasswordDto;
import com.snezana.doctorpractice.dto.MailDto;
import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.Doctor;
import com.snezana.doctorpractice.models.EvtWSMessage;
import com.snezana.doctorpractice.models.PatientRecord;
import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.models.EvtWSMessage.ActionType;
import com.snezana.doctorpractice.services.EventWSService;
import com.snezana.doctorpractice.services.UserService;
import com.snezana.doctorpractice.utils.SomeUtils;

/**
 * Controller class for patient activities.
 */
@Controller
@RequestMapping(value = "/home")
public class PatientController {

//	private static final Logger LOG = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	UserService userService;

	@Autowired
	private EventWSService eventWSService; // spring webSocket service

	@Autowired
	private MailComponent mailComponent;

	/**
	 * Page that gives the list of doctors available for appointments
	 */	
	@RequestMapping(value = "/patAppnmt", method = RequestMethod.GET)
	public String patAppnmt(ModelMap model, Principal principal) {
		model.addAttribute("allDoctors", userService.findAllDoctors());
		return "patAppnmt";
	}

	/**
	 * Page that gives the appointment scheduler of a doctor with particular id(doctorId) 
	 */
	@RequestMapping(value = "/patSchdl", method = RequestMethod.GET)
	public String patSchdl(ModelMap model, Principal principal,
			@RequestParam(value = "doctorId", required = true) Long doctorId) {
		Date date = SomeUtils.today();
		String username = principal.getName();
		User ptnUser = userService.findByUsername(username);
		Doctor doctor = userService.findDoctorById(doctorId);
		List<Appointment> listAppointm = userService.getAppointmentsFromTomorrow(doctor, date);
		model.addAttribute("listAppointm", listAppointm);
		List<AppointmentView> appViewList = userService.getAppntmViewFreeAndMineFromTomorrow(listAppointm,
				ptnUser.getPatient());
		model.addAttribute("appViewList", appViewList);
		model.addAttribute("dctrId", doctor.getDoctorId());
		model.addAttribute("ptnId", ptnUser.getPatient().getPatientId());
		String doctorName = doctor.getFirstName() + " " + doctor.getLastName();
		model.addAttribute("doctorName", doctorName);
		SomeUtils.setHourStrings(model);
		return "patSchdl";
	}

	/**
	 * Actions performed by patient (reserves/ cancels terms) on scheduler page (patSchdl).
	 * Actions include Spring WebSocket activities.
	 */
	@RequestMapping(value = "/appRec", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> appRec(Principal principal,
			@RequestParam(value = "tdid", required = true) String tdid,
			@RequestParam(value = "actn", required = true) String actn,
			@RequestParam(value = "doctorId", required = true) Long doctorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String username = principal.getName();
		User patUser = userService.findByUsername(username);
		Doctor doctor = userService.findDoctorById(doctorId);
		if (actn.equals("RSVDAX")) {
			userService.reserveAppointment(tdid, "RSVD", doctor, patUser.getPatient());
			map.put("message", "reserve novi appointment");
			EvtWSMessage evtMssg = new EvtWSMessage(tdid, ActionType.RSVDWS, doctor.getDoctorId(),
					patUser.getPatient().getPatientId());
			eventWSService.sendEventWS(evtMssg);
		} else {
			userService.patientCancelAppointment(tdid, "FREE", doctor);
			map.put("message", "otkazuje appointment");
			Long patientId = 3l;
			EvtWSMessage evtMssg = new EvtWSMessage(tdid, ActionType.PCLDWS, doctor.getDoctorId(), patientId);
			eventWSService.sendEventWS(evtMssg);
		}
		return map;
	}

	/**
	 * Page that gives the medical records list of patient with the particular username
	 */
	@RequestMapping(value = "/patViewRecords", method = RequestMethod.GET)
	public String viewPrint(ModelMap model, Principal principal) {
		String username = principal.getName();
		User pUser = userService.findByUsername(username);
		List<PatientRecord> patientRecordsList = userService.findAllRecordsByPatient(pUser.getPatient());
		model.addAttribute("patientRecordsList", patientRecordsList);
		String patientName = pUser.getPatient().getFirstName() + " " + pUser.getPatient().getLastName();
		model.addAttribute("patientName", patientName);
		return "patViewRecords";
	}

	/**
	 * The view of a medical report with the particular id
	 */
	@RequestMapping(value = "/patViewOneRecord", method = RequestMethod.GET)
	public String patViewPrintOneRecord(ModelMap model, Principal principal,
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
		return "patViewOneRecord";
	}

	/**
	 * Change patient password page
	 */
	@RequestMapping(value = "/patChangePassword", method = RequestMethod.GET)
	public String changePassword(ModelMap model) {
		model.addAttribute("changePasswordDto", new ChangePasswordDto());
		return "patChangePassword";
	}

	/**
	 * Action for checking old password and changing it by new one with data validation
	 */
	@RequestMapping(value = "/patChangePassword", method = RequestMethod.POST)
	public String changePassword2(@ModelAttribute("changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
			BindingResult bindingResult, ModelMap model, Principal principal) {
		String username = principal.getName();
		User user = userService.findByUsername(username);
		if (bindingResult.hasErrors()) {
			return "patChangePassword";
		}
		if (!userService.checkIfValidOldPassword(user, changePasswordDto.getOldPassword())) {
			return "redirect:/home/patChangePassword?err";
		}
		userService.changeUserPassword(user, changePasswordDto.getPassword());
		return "redirect:/home/patChangePassword?succ";
	}

	/**
	 * Chat Corner page
	 */
	@RequestMapping(value = "/patChat", method = RequestMethod.GET)
	public String chat(ModelMap model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "patChat";
	}

	/**
	 * Mail to admin page
	 */
	@RequestMapping(value = "/patMail", method = RequestMethod.GET)
	public String patientMail(ModelMap model, Principal principal) {
		String username = principal.getName();
		User ptUser = userService.findByUsername(username);
		MailDto mailDto = new MailDto();
		userService.patMailDtoSettings(mailDto, ptUser);
		model.addAttribute("mailDto", mailDto);
		return "patMail";
	}

	/**
	 * Action for sending mail to admin with data validation
	 */
	@RequestMapping(value = "/patMail", method = RequestMethod.POST)
	public String patientMail2(@ModelAttribute("mailDto") @Valid MailDto mailDto, BindingResult bindingResult,
			ModelMap model) {
		String formMessage = "";
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "patMail";
		}
		try {
			mailComponent.sendHtmlMail(mailDto);
		} catch (MailException e) {
			// catch error
//			LOG.info("Error sending Email: " + e.getMessage());
		}
		formMessage = "Mail was sent successfully.";
		model.addAttribute("formMessageScc", formMessage);
		return "patMail";
	}

}
