package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("appUserId")
    @Expose
    private Integer appUserId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
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
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("CountryCode")
    @Expose
    private Object countryCode;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Password")
    @Expose
    private Object password;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("EncryptedPassword")
    @Expose
    private String encryptedPassword;
    @SerializedName("RememberMe")
    @Expose
    private Integer rememberMe;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
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
    @SerializedName("Stateid")
    @Expose
    private Integer stateid;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("Avatarimage")
    @Expose
    private String avatarimage;
    @SerializedName("Postalcode")
    @Expose
    private String postalcode;
    @SerializedName("DoctorClinicAdmin")
    @Expose
    private Boolean doctorClinicAdmin;
    @SerializedName("ClAdminTitle")
    @Expose
    private Integer clAdminTitle;
    @SerializedName("ClAdminFirstName")
    @Expose
    private Object clAdminFirstName;
    @SerializedName("ClAdminLastName")
    @Expose
    private Object clAdminLastName;
    @SerializedName("ClAdminGender")
    @Expose
    private Object clAdminGender;
    @SerializedName("ClAdminCreatedBy")
    @Expose
    private Object clAdminCreatedBy;
    @SerializedName("ClAdminModifiedBy")
    @Expose
    private Object clAdminModifiedBy;
    @SerializedName("ClAdminCountryCode")
    @Expose
    private Object clAdminCountryCode;
    @SerializedName("clinicAddress1")
    @Expose
    private String clinicAddress1;
    @SerializedName("ClinicAddress2")
    @Expose
    private String clinicAddress2;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;

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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
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

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAvatarimage() {
        return avatarimage;
    }

    public void setAvatarimage(String avatarimage) {
        this.avatarimage = avatarimage;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public Boolean getDoctorClinicAdmin() {
        return doctorClinicAdmin;
    }

    public void setDoctorClinicAdmin(Boolean doctorClinicAdmin) {
        this.doctorClinicAdmin = doctorClinicAdmin;
    }

    public Integer getClAdminTitle() {
        return clAdminTitle;
    }

    public void setClAdminTitle(Integer clAdminTitle) {
        this.clAdminTitle = clAdminTitle;
    }

    public Object getClAdminFirstName() {
        return clAdminFirstName;
    }

    public void setClAdminFirstName(Object clAdminFirstName) {
        this.clAdminFirstName = clAdminFirstName;
    }

    public Object getClAdminLastName() {
        return clAdminLastName;
    }

    public void setClAdminLastName(Object clAdminLastName) {
        this.clAdminLastName = clAdminLastName;
    }

    public Object getClAdminGender() {
        return clAdminGender;
    }

    public void setClAdminGender(Object clAdminGender) {
        this.clAdminGender = clAdminGender;
    }

    public Object getClAdminCreatedBy() {
        return clAdminCreatedBy;
    }

    public void setClAdminCreatedBy(Object clAdminCreatedBy) {
        this.clAdminCreatedBy = clAdminCreatedBy;
    }

    public Object getClAdminModifiedBy() {
        return clAdminModifiedBy;
    }

    public void setClAdminModifiedBy(Object clAdminModifiedBy) {
        this.clAdminModifiedBy = clAdminModifiedBy;
    }

    public Object getClAdminCountryCode() {
        return clAdminCountryCode;
    }

    public void setClAdminCountryCode(Object clAdminCountryCode) {
        this.clAdminCountryCode = clAdminCountryCode;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
