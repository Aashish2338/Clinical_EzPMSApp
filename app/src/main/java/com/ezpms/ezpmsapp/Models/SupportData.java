package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportData {
    @SerializedName("appSupportId")
    @Expose
    private Integer appSupportId;
    @SerializedName("SupportId")
    @Expose
    private Integer supportId;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("SupportTypeId")
    @Expose
    private Integer supportTypeId;
    @SerializedName("SupportTypeValue")
    @Expose
    private String supportTypeValue;
    @SerializedName("SupportDetails")
    @Expose
    private String supportDetails;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("RcdInsTs")
    @Expose
    private String rcdInsTs;
    @SerializedName("RcdUpdtTs")
    @Expose
    private Object rcdUpdtTs;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("isDeleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("UniqueId")
    @Expose
    private Object uniqueId;
    @SerializedName("ReasonStatus")
    @Expose
    private Integer reasonStatus;
    @SerializedName("strReasonStatus")
    @Expose
    private String strReasonStatus;
    @SerializedName("srno")
    @Expose
    private Integer srno;
    @SerializedName("LocalInsUpdtTime")
    @Expose
    private Object localInsUpdtTime;
    @SerializedName("ServerInsUpdtTime")
    @Expose
    private Object serverInsUpdtTime;
    @SerializedName("SupportFullTitle")
    @Expose
    private Object supportFullTitle;

    public Integer getAppSupportId() {
        return appSupportId;
    }

    public void setAppSupportId(Integer appSupportId) {
        this.appSupportId = appSupportId;
    }

    public Integer getSupportId() {
        return supportId;
    }

    public void setSupportId(Integer supportId) {
        this.supportId = supportId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getSupportTypeId() {
        return supportTypeId;
    }

    public void setSupportTypeId(Integer supportTypeId) {
        this.supportTypeId = supportTypeId;
    }

    public String getSupportTypeValue() {
        return supportTypeValue;
    }

    public void setSupportTypeValue(String supportTypeValue) {
        this.supportTypeValue = supportTypeValue;
    }

    public String getSupportDetails() {
        return supportDetails;
    }

    public void setSupportDetails(String supportDetails) {
        this.supportDetails = supportDetails;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getRcdInsTs() {
        return rcdInsTs;
    }

    public void setRcdInsTs(String rcdInsTs) {
        this.rcdInsTs = rcdInsTs;
    }

    public Object getRcdUpdtTs() {
        return rcdUpdtTs;
    }

    public void setRcdUpdtTs(Object rcdUpdtTs) {
        this.rcdUpdtTs = rcdUpdtTs;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public Object getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Object uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getReasonStatus() {
        return reasonStatus;
    }

    public void setReasonStatus(Integer reasonStatus) {
        this.reasonStatus = reasonStatus;
    }

    public String getStrReasonStatus() {
        return strReasonStatus;
    }

    public void setStrReasonStatus(String strReasonStatus) {
        this.strReasonStatus = strReasonStatus;
    }

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
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

    public Object getSupportFullTitle() {
        return supportFullTitle;
    }

    public void setSupportFullTitle(Object supportFullTitle) {
        this.supportFullTitle = supportFullTitle;
    }
}
