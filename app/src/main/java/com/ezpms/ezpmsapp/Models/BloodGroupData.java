package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodGroupData {
    @SerializedName("BloodGroupId")
    @Expose
    private Integer bloodGroupId;
    @SerializedName("uniqueId")
    @Expose
    private Object uniqueId;
    @SerializedName("BloodGroupName")
    @Expose
    private String bloodGroupName;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("RcdInsTs")
    @Expose
    private String rcdInsTs;
    @SerializedName("RcdUpdtTs")
    @Expose
    private String rcdUpdtTs;

    public Integer getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(Integer bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public Object getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Object uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getBloodGroupName() {
        return bloodGroupName;
    }

    public void setBloodGroupName(String bloodGroupName) {
        this.bloodGroupName = bloodGroupName;
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
}
