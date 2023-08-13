package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSloteData {
    @SerializedName("appTimeSlotsId")
    @Expose
    private Integer appTimeSlotsId;
    @SerializedName("TimeSlotId")
    @Expose
    private Integer timeSlotId;
    @SerializedName("TimeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("TimeTo")
    @Expose
    private String timeTo;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("fullday")
    @Expose
    private Object fullday;
    @SerializedName("FullNameOrderBy")
    @Expose
    private Object fullNameOrderBy;
    @SerializedName("TimeFromOnly")
    @Expose
    private Object timeFromOnly;
    @SerializedName("FullName")
    @Expose
    private Object fullName;
    @SerializedName("TimeDuration")
    @Expose
    private Object timeDuration;

    public Integer getAppTimeSlotsId() {
        return appTimeSlotsId;
    }

    public void setAppTimeSlotsId(Integer appTimeSlotsId) {
        this.appTimeSlotsId = appTimeSlotsId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
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

    public Object getFullday() {
        return fullday;
    }

    public void setFullday(Object fullday) {
        this.fullday = fullday;
    }

    public Object getFullNameOrderBy() {
        return fullNameOrderBy;
    }

    public void setFullNameOrderBy(Object fullNameOrderBy) {
        this.fullNameOrderBy = fullNameOrderBy;
    }

    public Object getTimeFromOnly() {
        return timeFromOnly;
    }

    public void setTimeFromOnly(Object timeFromOnly) {
        this.timeFromOnly = timeFromOnly;
    }

    public Object getFullName() {
        return fullName;
    }

    public void setFullName(Object fullName) {
        this.fullName = fullName;
    }

    public Object getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Object timeDuration) {
        this.timeDuration = timeDuration;
    }
}
