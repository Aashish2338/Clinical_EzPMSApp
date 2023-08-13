package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorAvailableData {
    @SerializedName("DoctorId")
    @Expose
    private Integer doctorId;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("UserProfileId")
    @Expose
    private Integer userProfileId;
    @SerializedName("UserName")
    @Expose
    private Object userName;
    @SerializedName("Roll")
    @Expose
    private String roll;
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
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("ClinicAddress")
    @Expose
    private Object clinicAddress;
    @SerializedName("OtherAddress")
    @Expose
    private Object otherAddress;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("PostalCode")
    @Expose
    private Object postalCode;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("UserEmail")
    @Expose
    private Object userEmail;
    @SerializedName("strPasswordHash")
    @Expose
    private Object strPasswordHash;
    @SerializedName("ConcurrencyStamp")
    @Expose
    private Object concurrencyStamp;
    @SerializedName("PhoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("StartPatientId")
    @Expose
    private Integer startPatientId;
    @SerializedName("PackageId")
    @Expose
    private Integer packageId;
    @SerializedName("PackageSubscribed")
    @Expose
    private Boolean packageSubscribed;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("VerificationCode")
    @Expose
    private Object verificationCode;
    @SerializedName("AvatarImage")
    @Expose
    private Object avatarImage;
    @SerializedName("EncryptedPassword")
    @Expose
    private Object encryptedPassword;
    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("DoctorClinicAdmin")
    @Expose
    private Boolean doctorClinicAdmin;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
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

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Object getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(Object clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public Object getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(Object otherAddress) {
        this.otherAddress = otherAddress;
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

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
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

    public Object getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Object userEmail) {
        this.userEmail = userEmail;
    }

    public Object getStrPasswordHash() {
        return strPasswordHash;
    }

    public void setStrPasswordHash(Object strPasswordHash) {
        this.strPasswordHash = strPasswordHash;
    }

    public Object getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public void setConcurrencyStamp(Object concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Boolean getPackageSubscribed() {
        return packageSubscribed;
    }

    public void setPackageSubscribed(Boolean packageSubscribed) {
        this.packageSubscribed = packageSubscribed;
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

    public Object getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Object verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Object getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(Object avatarImage) {
        this.avatarImage = avatarImage;
    }

    public Object getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(Object encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getDoctorClinicAdmin() {
        return doctorClinicAdmin;
    }

    public void setDoctorClinicAdmin(Boolean doctorClinicAdmin) {
        this.doctorClinicAdmin = doctorClinicAdmin;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }
}
