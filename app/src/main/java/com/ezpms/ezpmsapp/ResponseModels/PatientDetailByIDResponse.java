package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.PatientDetailByID;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDetailByIDResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private PatientDetailByID patientDetailByID;

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

    public PatientDetailByID getPatientDetailByID() {
        return patientDetailByID;
    }

    public void setPatientDetailByID(PatientDetailByID patientDetailByID) {
        this.patientDetailByID = patientDetailByID;
    }
}
