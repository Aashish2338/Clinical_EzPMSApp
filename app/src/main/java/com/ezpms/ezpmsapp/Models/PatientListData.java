package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientListData {
    @SerializedName("PatientId")
    @Expose
    private Integer patientId;
    @SerializedName("Title")
    @Expose
    private Integer title;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("PatientType")
    @Expose
    private Integer patientType;
    @SerializedName("Age")
    @Expose
    private Object age;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("CityName")
    @Expose
    private Object cityName;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("StateName")
    @Expose
    private Object stateName;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("PostalCode")
    @Expose
    private Object postalCode;
    @SerializedName("Address1")
    @Expose
    private Object address1;
    @SerializedName("Address2")
    @Expose
    private Object address2;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("IsActive")
    @Expose
    private Object isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Object isDeleted;
    @SerializedName("RcdInsTs")
    @Expose
    private Object rcdInsTs;
    @SerializedName("RcdUpdtTs")
    @Expose
    private Object rcdUpdtTs;
    @SerializedName("UniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("PatientIdDisp")
    @Expose
    private Object patientIdDisp;
    @SerializedName("AppointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("PatientName")
    @Expose
    private Object patientName;
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
    private Object otherMedicalHistory;
    @SerializedName("LastVisitDate")
    @Expose
    private String lastVisitDate;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Integer getPatientType() {
        return patientType;
    }

    public void setPatientType(Integer patientType) {
        this.patientType = patientType;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
        this.cityName = cityName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Object getStateName() {
        return stateName;
    }

    public void setStateName(Object stateName) {
        this.stateName = stateName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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

    public Object getPatientName() {
        return patientName;
    }

    public void setPatientName(Object patientName) {
        this.patientName = patientName;
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

    public Object getOtherMedicalHistory() {
        return otherMedicalHistory;
    }

    public void setOtherMedicalHistory(Object otherMedicalHistory) {
        this.otherMedicalHistory = otherMedicalHistory;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
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
}
