package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetailByID {
    @SerializedName("PatientId")
    @Expose
    private Integer patientId;
    @SerializedName("PatientIdDisp")
    @Expose
    private Object patientIdDisp;
    @SerializedName("AppointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("Title")
    @Expose
    private Integer title;
    @SerializedName("PatientName")
    @Expose
    private String patientName;
    @SerializedName("FirstName")
    @Expose
    private Object firstName;
    @SerializedName("LastName")
    @Expose
    private Object lastName;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("BloodGroup")
    @Expose
    private Integer bloodGroup;
    @SerializedName("MedicalHistory")
    @Expose
    private Integer medicalHistory;
    @SerializedName("OtherMedicalHistory")
    @Expose
    private String otherMedicalHistory;
    @SerializedName("LastVisitDate")
    @Expose
    private Object lastVisitDate;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Address1")
    @Expose
    private Object address1;
    @SerializedName("Address2")
    @Expose
    private Object address2;
    @SerializedName("PostalCode")
    @Expose
    private Object postalCode;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("ClinicName")
    @Expose
    private Object clinicName;
    @SerializedName("ClinicAddress")
    @Expose
    private Object clinicAddress;
    @SerializedName("ClinicAddress2")
    @Expose
    private Object clinicAddress2;
    @SerializedName("ClinicPinCode")
    @Expose
    private Object clinicPinCode;
    @SerializedName("ClinicCityId")
    @Expose
    private Integer clinicCityId;
    @SerializedName("ClinicStateId")
    @Expose
    private Integer clinicStateId;
    @SerializedName("ClinicCountryId")
    @Expose
    private Integer clinicCountryId;
    @SerializedName("UniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Object isDeleted;
    @SerializedName("RcdInsTs")
    @Expose
    private Object rcdInsTs;
    @SerializedName("RcdUpdtTs")
    @Expose
    private Object rcdUpdtTs;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Object getPatientIdDisp() {
        return patientIdDisp;
    }

    public void setPatientIdDisp(Object patientIdDisp) {
        this.patientIdDisp = patientIdDisp;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Integer bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(Integer medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getOtherMedicalHistory() {
        return otherMedicalHistory;
    }

    public void setOtherMedicalHistory(String otherMedicalHistory) {
        this.otherMedicalHistory = otherMedicalHistory;
    }

    public Object getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Object lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getAddress1() {
        return address1;
    }

    public void setAddress1(Object address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Object getClinicName() {
        return clinicName;
    }

    public void setClinicName(Object clinicName) {
        this.clinicName = clinicName;
    }

    public Object getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(Object clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public Object getClinicAddress2() {
        return clinicAddress2;
    }

    public void setClinicAddress2(Object clinicAddress2) {
        this.clinicAddress2 = clinicAddress2;
    }

    public Object getClinicPinCode() {
        return clinicPinCode;
    }

    public void setClinicPinCode(Object clinicPinCode) {
        this.clinicPinCode = clinicPinCode;
    }

    public Integer getClinicCityId() {
        return clinicCityId;
    }

    public void setClinicCityId(Integer clinicCityId) {
        this.clinicCityId = clinicCityId;
    }

    public Integer getClinicStateId() {
        return clinicStateId;
    }

    public void setClinicStateId(Integer clinicStateId) {
        this.clinicStateId = clinicStateId;
    }

    public Integer getClinicCountryId() {
        return clinicCountryId;
    }

    public void setClinicCountryId(Integer clinicCountryId) {
        this.clinicCountryId = clinicCountryId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Object isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getRcdInsTs() {
        return rcdInsTs;
    }

    public void setRcdInsTs(Object rcdInsTs) {
        this.rcdInsTs = rcdInsTs;
    }

    public Object getRcdUpdtTs() {
        return rcdUpdtTs;
    }

    public void setRcdUpdtTs(Object rcdUpdtTs) {
        this.rcdUpdtTs = rcdUpdtTs;
    }
}
