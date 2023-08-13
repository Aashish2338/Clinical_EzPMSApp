package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportTypeData {
    @SerializedName("SupportId")
    @Expose
    private Integer supportId;
    @SerializedName("SupportTypeValue")
    @Expose
    private String supportTypeValue;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getSupportId() {
        return supportId;
    }

    public void setSupportId(Integer supportId) {
        this.supportId = supportId;
    }

    public String getSupportTypeValue() {
        return supportTypeValue;
    }

    public void setSupportTypeValue(String supportTypeValue) {
        this.supportTypeValue = supportTypeValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
