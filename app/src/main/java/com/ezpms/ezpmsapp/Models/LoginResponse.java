package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("appclinicId")
    @Expose
    private Integer appclinicId;
    @SerializedName("clinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("title")
    @Expose
    private Integer title;
    @SerializedName("clinicName")
    @Expose
    private String clinicName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("clinicAddress1")
    @Expose
    private String clinicAddress1;
    @SerializedName("clinicAddress2")
    @Expose
    private String clinicAddress2;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("userpassword")
    @Expose
    private String userpassword;
    @SerializedName("isEmailSignUp")
    @Expose
    private Integer isEmailSignUp;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("startPatientId")
    @Expose
    private Integer startPatientId;
    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("rcdInsTs")
    @Expose
    private String rcdInsTs;
    @SerializedName("rcdUpdtTs")
    @Expose
    private String rcdUpdtTs;
    @SerializedName("concurrencyStamp")
    @Expose
    private String concurrencyStamp;
    @SerializedName("rememberMe")
    @Expose
    private Integer rememberMe;
    @SerializedName("isVerified")
    @Expose
    private Boolean isVerified;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("avatarImage")
    @Expose
    private String avatarImage;
    @SerializedName("emailConfirmed")
    @Expose
    private Boolean emailConfirmed;
    @SerializedName("phoneNumberConfirmed")
    @Expose
    private Boolean phoneNumberConfirmed;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("encryptedPassword")
    @Expose
    private String encryptedPassword;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("verificationRemainingDays")
    @Expose
    private Integer verificationRemainingDays;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("doctorClinicAdmin")
    @Expose
    private Boolean doctorClinicAdmin;
    @SerializedName("localInsUpdtTime")
    @Expose
    private String localInsUpdtTime;
    @SerializedName("serverInsUpdtTime")
    @Expose
    private String serverInsUpdtTime;

    public Integer getAppclinicId() {
        return appclinicId;
    }

    public void setAppclinicId(Integer appclinicId) {
        this.appclinicId = appclinicId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
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

    public String getClinicAddress1() {
        return clinicAddress1;
    }

    public void setClinicAddress1(String clinicAddress1) {
        this.clinicAddress1 = clinicAddress1;
    }

    public String getClinicAddress2() {
        return clinicAddress2;
    }

    public void setClinicAddress2(String clinicAddress2) {
        this.clinicAddress2 = clinicAddress2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public Integer getIsEmailSignUp() {
        return isEmailSignUp;
    }

    public void setIsEmailSignUp(Integer isEmailSignUp) {
        this.isEmailSignUp = isEmailSignUp;
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStartPatientId() {
        return startPatientId;
    }

    public void setStartPatientId(Integer startPatientId) {
        this.startPatientId = startPatientId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
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

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public void setConcurrencyStamp(String concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getVerificationRemainingDays() {
        return verificationRemainingDays;
    }

    public void setVerificationRemainingDays(Integer verificationRemainingDays) {
        this.verificationRemainingDays = verificationRemainingDays;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getDoctorClinicAdmin() {
        return doctorClinicAdmin;
    }

    public void setDoctorClinicAdmin(Boolean doctorClinicAdmin) {
        this.doctorClinicAdmin = doctorClinicAdmin;
    }

    public String getLocalInsUpdtTime() {
        return localInsUpdtTime;
    }

    public void setLocalInsUpdtTime(String localInsUpdtTime) {
        this.localInsUpdtTime = localInsUpdtTime;
    }

    public String getServerInsUpdtTime() {
        return serverInsUpdtTime;
    }

    public void setServerInsUpdtTime(String serverInsUpdtTime) {
        this.serverInsUpdtTime = serverInsUpdtTime;
    }
}
