package com.ezpms.ezpmsapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentCountData {
    @SerializedName("AllAppintment")
    @Expose
    private Integer allAppintment;
    @SerializedName("Completed")
    @Expose
    private Integer completed;
    @SerializedName("pending")
    @Expose
    private Integer pending;

    public Integer getAllAppintment() {
        return allAppintment;
    }

    public void setAllAppintment(Integer allAppintment) {
        this.allAppintment = allAppintment;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }
}
