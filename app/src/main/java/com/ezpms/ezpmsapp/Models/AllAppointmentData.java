package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAppointmentData {
    @SerializedName("appAppointmentId")
    @Expose
    private Integer appAppointmentId;
    @SerializedName("AppointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("appPatientId")
    @Expose
    private Integer appPatientId;
    @SerializedName("PatientId")
    @Expose
    private Integer patientId;
    @SerializedName("appUserId")
    @Expose
    private Integer appUserId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("AppointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("ConsultationType")
    @Expose
    private Integer consultationType;
    @SerializedName("AppointmentStatus")
    @Expose
    private Integer appointmentStatus;
    @SerializedName("TimeSlot")
    @Expose
    private Integer timeSlot;
    @SerializedName("KeyNotes")
    @Expose
    private String keyNotes;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("rcdInsTs")
    @Expose
    private String rcdInsTs;
    @SerializedName("rcdUpdtTs")
    @Expose
    private String rcdUpdtTs;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("isCancelled")
    @Expose
    private Boolean isCancelled;
    @SerializedName("CancellationReason")
    @Expose
    private Object cancellationReason;
    @SerializedName("CancellationUpdtTs")
    @Expose
    private Object cancellationUpdtTs;
    @SerializedName("LocalInsUpdtTime")
    @Expose
    private Object localInsUpdtTime;
    @SerializedName("ServerInsUpdtTime")
    @Expose
    private Object serverInsUpdtTime;
    @SerializedName("PatientFirstName")
    @Expose
    private String patientFirstName;
    @SerializedName("PatientLastName")
    @Expose
    private String patientLastName;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("DoctorName")
    @Expose
    private Object doctorName;
    @SerializedName("TimeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("TimeTo")
    @Expose
    private String timeTo;

    @SerializedName("ConsultationTypeName")
    @Expose
    private Object consultationTypeName;
    @SerializedName("TreatmentId")
    @Expose
    private Integer treatmentId;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("TreatmentDate")
    @Expose
    private String treatmentDate;
    @SerializedName("DrPhoneNumber")
    @Expose
    private String drPhoneNumber;
    @SerializedName("DiseaseName")
    @Expose
    private Object diseaseName;
    @SerializedName("DiseaseSymptoms")
    @Expose
    private Object diseaseSymptoms;
    @SerializedName("DiseaseTreatment")
    @Expose
    private Object diseaseTreatment;
    @SerializedName("DiseaseMedicinePrescribed")
    @Expose
    private Object diseaseMedicinePrescribed;

    public Integer getAppAppointmentId() {
        return appAppointmentId;
    }

    public void setAppAppointmentId(Integer appAppointmentId) {
        this.appAppointmentId = appAppointmentId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getAppPatientId() {
        return appPatientId;
    }

    public void setAppPatientId(Integer appPatientId) {
        this.appPatientId = appPatientId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(Integer consultationType) {
        this.consultationType = consultationType;
    }

    public Integer getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(Integer appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getKeyNotes() {
        return keyNotes;
    }

    public void setKeyNotes(String keyNotes) {
        this.keyNotes = keyNotes;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getRcdInsTs() {
        return rcdInsTs;
    }

    public void setRcdInsTs(String rcdInsTs) {
        this.rcdInsTs = rcdInsTs;
    }

    public String getRcdUpdtTs() {
        return rcdUpdtTs;
    }

    public void setRcdUpdtTs(String rcdUpdtTs) {
        this.rcdUpdtTs = rcdUpdtTs;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Object getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(Object cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Object getCancellationUpdtTs() {
        return cancellationUpdtTs;
    }

    public void setCancellationUpdtTs(Object cancellationUpdtTs) {
        this.cancellationUpdtTs = cancellationUpdtTs;
    }

    public Object getLocalInsUpdtTime() {
        return localInsUpdtTime;
    }

    public void setLocalInsUpdtTime(Object localInsUpdtTime) {
        this.localInsUpdtTime = localInsUpdtTime;
    }

    public Object getServerInsUpdtTime() {
        return serverInsUpdtTime;
    }

    public void setServerInsUpdtTime(Object serverInsUpdtTime) {
        this.serverInsUpdtTime = serverInsUpdtTime;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Object getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(Object doctorName) {
        this.doctorName = doctorName;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Object getConsultationTypeName() {
        return consultationTypeName;
    }

    public void setConsultationTypeName(Object consultationTypeName) {
        this.consultationTypeName = consultationTypeName;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getDrPhoneNumber() {
        return drPhoneNumber;
    }

    public void setDrPhoneNumber(String drPhoneNumber) {
        this.drPhoneNumber = drPhoneNumber;
    }

    public Object getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(Object diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Object getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    public void setDiseaseSymptoms(Object diseaseSymptoms) {
        this.diseaseSymptoms = diseaseSymptoms;
    }

    public Object getDiseaseTreatment() {
        return diseaseTreatment;
    }

    public void setDiseaseTreatment(Object diseaseTreatment) {
        this.diseaseTreatment = diseaseTreatment;
    }

    public Object getDiseaseMedicinePrescribed() {
        return diseaseMedicinePrescribed;
    }

    public void setDiseaseMedicinePrescribed(Object diseaseMedicinePrescribed) {
        this.diseaseMedicinePrescribed = diseaseMedicinePrescribed;
    }
}