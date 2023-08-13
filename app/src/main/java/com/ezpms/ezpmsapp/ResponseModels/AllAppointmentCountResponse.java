package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.AppointmentCountData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAppointmentCountResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private AppointmentCountData appointmentCountData;

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

    public AppointmentCountData getAppointmentCountData() {
        return appointmentCountData;
    }

    public void setAppointmentCountData(AppointmentCountData appointmentCountData) {
        this.appointmentCountData = appointmentCountData;
    }
}
