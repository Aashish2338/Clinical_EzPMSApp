package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAppUsersData {
    @SerializedName("appUserId")
    @Expose
    private Integer appUserId;
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
    private String gender;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("rcdInsTs")
    @Expose
    private String rcdInsTs;
    @SerializedName("rcdUpdtTs")
    @Expose
    private String rcdUpdtTs;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("EncryptedPassword")
    @Expose
    private String encryptedPassword;
    @SerializedName("RememberMe")
    @Expose
    private Integer rememberMe;
    @SerializedName("DoctorClinicAdmin")
    @Expose
    private Boolean doctorClinicAdmin;
    @SerializedName("LastLogin")
    @Expose
    private String lastLogin;
    @SerializedName("LocalInsUpdtTime")
    @Expose
    private Object localInsUpdtTime;
    @SerializedName("ServerInsUpdtTime")
    @Expose
    private String serverInsUpdtTime;

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Boolean getDoctorClinicAdmin() {
        return doctorClinicAdmin;
    }

    public void setDoctorClinicAdmin(Boolean doctorClinicAdmin) {
        this.doctorClinicAdmin = doctorClinicAdmin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getLocalInsUpdtTime() {
        return localInsUpdtTime;
    }

    public void setLocalInsUpdtTime(Object localInsUpdtTime) {
        this.localInsUpdtTime = localInsUpdtTime;
    }

    public String getServerInsUpdtTime() {
        return serverInsUpdtTime;
    }

    public void setServerInsUpdtTime(String serverInsUpdtTime) {
        this.serverInsUpdtTime = serverInsUpdtTime;
    }
}
