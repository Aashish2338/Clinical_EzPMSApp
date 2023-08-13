package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistoryData {
    @SerializedName("MedicalHistoryId")
    @Expose
    private Integer medicalHistoryId;
    @SerializedName("MedicalHistoryName")
    @Expose
    private String medicalHistoryName;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("RcdInsTs")
    @Expose
    private Object rcdInsTs;
    @SerializedName("RcdUpdtTs")
    @Expose
    private Object rcdUpdtTs;

    public Integer getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public void setMedicalHistoryId(Integer medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    public String getMedicalHistoryName() {
        return medicalHistoryName;
    }

    public void setMedicalHistoryName(String medicalHistoryName) {
        this.medicalHistoryName = medicalHistoryName;
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
