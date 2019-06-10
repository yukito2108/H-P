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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.snezana.doctorpractice.configurations.MailComponent;
import com.snezana.doctorpractice.dto.AppointmentView;
import com.snezana.doctorpractice.dto.ChangePasswordDto;
import com.snezana.doctorpractice.dto.MailDto;
import com.snezana.doctorpractice.dto.PatientRecordDto;
import com.snezana.doctorpractice.models.Appointment;
import com.snezana.doctorpractice.models.EvtWSMessage;
import com.snezana.doctorpractice.models.EvtWSMessage.ActionType;
import com.snezana.doctorpractice.models.PatientRecord;
import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.services.EventWSService;
import com.snezana.doctorpractice.services.UserService;
import com.snezana.doctorpractice.utils.PdfGeneratorUtil;
import com.snezana.doctorpractice.utils.SomeUtils;

/**
 * Controller class for doctor's activities.
 */
@Controller
@RequestMapping(value = "/doctor")
public class DoctorController {

	// private static final Logger LOG = LoggerFactory.getLogger(DoctorController.class); 

	@Autowired
	UserService userService;

	@Autowired
	private EventWSService eventWSService; // spring webSocket service

	@Autowired
	PdfGeneratorUtil pdfGeneratorUtil;

	@Autowired
	private MailComponent mailComponent;

	/**
	 * Page that gives the list of all patients
	 */	
	@RequestMapping(value = "/docPatRec", method = RequestMethod.GET)
	public String docPatRec(ModelMap model) {
		model.addAttribute("allPatients", userService.findAllValidPatients());
		return "docPatRec";
	}

	/**
	 * Info page of a patient with particular id
	 */	
	@RequestMapping(value = "/dInfoPtn", method = RequestMethod.GET)
	public String infoPtn(ModelMap model, @RequestParam(value = "id", required = true) Long id) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("birthDate", SomeUtils.dateView(user.getPatient().getBirthDate()));
		return "dInfoPtn";
	}

	/**
	 * Create a new record (medical report) page
	 */
	@RequestMapping(value = "/createNewRecord", method = RequestMethod.GET)
	public String createReport(ModelMap model, Principal principal,
			@RequestParam(value = "id", required = true) Long id) {
		String username = principal.getName();
		User drUser = userService.findByUsername(username);
		User pUser = userService.findUserById(id);
		PatientRecordDto patientRecordDto = new PatientRecordDto();
		userService.patientRecordDtoSettings(patientRecordDto, pUser, drUser);
		model.addAttribute("patientRecordDto", patientRecordDto);
		return "createNewRecord";
	}

	/**
	 * New medical report creation with data validation
	 */
	@RequestMapping(value = "/createNewRecord", method = RequestMethod.POST)
	public String createReport2(@ModelAttribute("patientRecordDto") @Valid PatientRecordDto patientRecordDto,
			BindingResult bindingResult, ModelMap model) {
		String formMessage = "";
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "createNewRecord";
		}
		userService.createNewRecord(patientRecordDto);
		formMessage = "Form successfully completed and added to DB!";
		model.addAttribute("formMessageScc", formMessage);
		return "createNewRecord";
	}

	/**
	 * Page that gives the appointment scheduler 
	 */
	@RequestMapping(value = "/docSchdl", method = RequestMethod.GET)
	public String docSchdl(ModelMap model, Principal principal) {
		String username = principal.getName();
		User drUser = userService.findByUsername(username);
		Date date = SomeUtils.today();
		List<Appointment> listAppointm = userService.getAppointmentsFromTomorrow(drUser.getDoctor(), date);
		model.addAttribute("listAppointm", listAppointm);
		List<AppointmentView> appViewList = userService.getAppointmentsViewFromTomorrow(listAppointm);
		model.addAttribute("appViewList", appViewList);
		String doctorName = drUser.getDoctor().getFirstName() + " " + drUser.getDoctor().getLastName();
		model.addAttribute("doctorName", doctorName);
		model.addAttribute("dctrId", drUser.getDoctor().getDoctorId());
		SomeUtils.setHourStrings(model);
		return "docSchdl";
	}

	/**
	 * Actions performed by doctor (opens/ cancels terms) on scheduler page (docSchdl).
	 * Actions include Spring WebSocket activities.
	 */
	@RequestMapping(value = "/appRec", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> appRec(Principal principal,
			@RequestParam(value = "tdid", required = true) String tdid,
			@RequestParam(value = "actn", required = true) String actn) {
		Map<String, Object> map = new HashMap<String, Object>();
		String username = principal.getName();
		User drUser = userService.findByUsername(username);
		Long patientId = 3l;
		if (actn.equals("FREEAX")) {
			userService.createNewAppointment(tdid, "FREE", drUser.getDoctor());
			map.put("message", "kreira novi appointment");
			EvtWSMessage evtMssg = new EvtWSMessage(tdid, ActionType.FREEWS, drUser.getDoctor().getDoctorId(),
					patientId);
			eventWSService.sendEventWS(evtMssg);
		} else {
			userService.cancelAppointment(tdid, drUser.getDoctor());
			map.put("message", "otkazuje appointment");
			EvtWSMessage evtMssg = new EvtWSMessage(tdid, ActionType.CNLDWS, drUser.getDoctor().getDoctorId(),
					patientId);
			eventWSService.sendEventWS(evtMssg);
		}
		return map;
	}

	/**
	 * Page that gives the appointment list of a doctor
	 */
	@RequestMapping(value = "/docAppnmtLst", method = RequestMethod.GET)
	public String docAppnmtLst(ModelMap model, Principal principal) {
		String username = principal.getName();
		User drUser = userService.findByUsername(username);
		Date date = SomeUtils.today();
		List<Appointment> listAppointm = userService.getAppointmentsFromTomorrow(drUser.getDoctor(), date);
		model.addAttribute("listaAppntm", listAppointm);
		List<AppointmentView> appViewList = userService.getAppointmentsViewFromTomorrow(listAppointm);
		model.addAttribute("appViewList", appViewList);
		String doctorName = drUser.getDoctor().getFirstName() + " " + drUser.getDoctor().getLastName();
		model.addAttribute("doctorName", doctorName);
		return "docAppnmtLst";
	}

	/**
	 * Chat Corner page
	 */
	@RequestMapping(value = "/docChat", method = RequestMethod.GET)
	public String chat(ModelMap model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "docChat";
	}

	/**
	 * Records list of a patient with particular id
	 */
	@RequestMapping(value = "/viewPrintRecords", method = RequestMethod.GET)
	public String viewPrint(ModelMap model, Principal principal, @RequestParam(value = "id", required = true) Long id) {
		User pUser = userService.findUserById(id);
		List<PatientRecord> patientRecordsList = userService.findAllRecordsByPatient(pUser.getPatient());
		model.addAttribute("patientRecordsList", patientRecordsList);
		String patientName = pUser.getPatient().getFirstName() + " " + pUser.getPatient().getLastName();
		model.addAttribute("patientName", patientName);
		return "viewPrintRecords";
	}

	/**
	 * View of one medical report with particular id 
	 */
	@RequestMapping(value = "/viewPrintOneRecord", method = RequestMethod.GET)
	public String docViewPrintOneRecord(ModelMap model, Principal principal,
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
		return "viewPrintOneRecord";
	}

	/**
	 * Print to PDF action of one medical report with particular id 
	 */
	@RequestMapping(value = "/viewPrintOneRecordToPDF", method = RequestMethod.GET)
	public String docViewPrintOneRecord2(@RequestParam(value = "id", required = true) Long id, ModelMap model,
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
		return "redirect:/doctor/viewPrintOneRecord";
	}

	/**
	 * Mail to admin page
	 */
	@RequestMapping(value = "/docMail", method = RequestMethod.GET)
	public String doctorMail(ModelMap model, Principal principal) {
		String username = principal.getName();
		User drUser = userService.findByUsername(username);
		MailDto mailDto = new MailDto();
		userService.docMailDtoSettings(mailDto, drUser);
		model.addAttribute("mailDto", mailDto);
		return "docMail";
	}

	/**
	 * Action for sending mail to admin with data validation
	 */
	@RequestMapping(value = "/docMail", method = RequestMethod.POST)
	public String doctorMail2(@ModelAttribute("mailDto") @Valid MailDto mailDto, BindingResult bindingResult,
			ModelMap model) {
		String formMessage = "";
		if (bindingResult.hasErrors()) {
			formMessage = "Form has errors!";
			model.addAttribute("formMessageErr", formMessage);
			return "docMail";
		}
		try {
			mailComponent.sendHtmlMail(mailDto);
		} catch (MailException e) {
			// catch error
			// LOG.info("Error sending Email: " + e.getMessage());
		}
		formMessage = "Mail was sent successfully.";
		model.addAttribute("formMessageScc", formMessage);
		return "docMail";
	}

	/**
	 * Change doctor password page
	 */
	@RequestMapping(value = "/docChangePassword", method = RequestMethod.GET)
	public String changePassword(ModelMap model) {
		model.addAttribute("changePasswordDto", new ChangePasswordDto());
		return "docChangePassword";
	}

	/**
	 * Action for checking old password and changing it by new one with data validation
	 */
	@RequestMapping(value = "/docChangePassword", method = RequestMethod.POST)
	public String changePassword2(@ModelAttribute("changePasswordDto") @Valid ChangePasswordDto changePasswordDto,
			BindingResult bindingResult, ModelMap model, Principal principal) {
		String username = principal.getName();
		User user = userService.findByUsername(username);
		if (bindingResult.hasErrors()) {
			return "docChangePassword";
		}
		if (!userService.checkIfValidOldPassword(user, changePasswordDto.getOldPassword())) {
			return "redirect:/doctor/docChangePassword?err";
		}
		userService.changeUserPassword(user, changePasswordDto.getPassword());
		return "redirect:/doctor/docChangePassword?succ";
	}

}
