package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.DoctorAvailableData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorAvailableResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<DoctorAvailableData> doctorAvailableData = null;

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

    public List<DoctorAvailableData> getDoctorAvailableData() {
        return doctorAvailableData;
    }

    public void setDoctorAvailableData(List<DoctorAvailableData> doctorAvailableData) {
        this.doctorAvailableData = doctorAvailableData;
    }
}
