package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultationTypesData {
    @SerializedName("appConsultationTypeId")
    @Expose
    private Integer appConsultationTypeId;
    @SerializedName("ConsultationTypeId")
    @Expose
    private Integer consultationTypeId;
    @SerializedName("ConsultationType")
    @Expose
    private String consultationType;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getAppConsultationTypeId() {
        return appConsultationTypeId;
    }

    public void setAppConsultationTypeId(Integer appConsultationTypeId) {
        this.appConsultationTypeId = appConsultationTypeId;
    }

    public Integer getConsultationTypeId() {
        return consultationTypeId;
    }

    public void setConsultationTypeId(Integer consultationTypeId) {
        this.consultationTypeId = consultationTypeId;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
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
}
