package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.AppointmentStatusData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentStatusResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<AppointmentStatusData> appointmentStatusData = null;

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<AppointmentStatusData> getAppointmentStatusData() {
        return appointmentStatusData;
    }

    public void setAppointmentStatusData(List<AppointmentStatusData> appointmentStatusData) {
        this.appointmentStatusData = appointmentStatusData;
    }
}
