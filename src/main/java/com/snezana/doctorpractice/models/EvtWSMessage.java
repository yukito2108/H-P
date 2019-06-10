package com.snezana.doctorpractice.models;

/**
 * WebSocket event message format for scheduler
 */
public class EvtWSMessage {

	private String appDatetime;
	private ActionType action;
	private Long doctorId;
	private Long patientId;

	public enum ActionType {
		/**
		 * FREEWS - free term (is set by doctor)
		 * RSVDWS - reserved term (is set by patient)
		 * CNLDWS - canceled term set by doctor
		 * PCLDWS - canceled term set by patient
		 */
		FREEWS, RSVDWS, CNLDWS, PCLDWS
	}

	public EvtWSMessage(final String appDatetime, final ActionType action, final Long doctorId, final Long patientId) {
		this.appDatetime = appDatetime;
		this.action = action;
		this.doctorId = doctorId;
		this.patientId = patientId;
	}

	public EvtWSMessage(String appDatetime, ActionType action, Long doctorId) {
		this.appDatetime = appDatetime;
		this.action = action;
		this.doctorId = doctorId;
	}

	public String getAppDatetime() {
		return appDatetime;
	}

	public ActionType getAction() {
		return action;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

}
