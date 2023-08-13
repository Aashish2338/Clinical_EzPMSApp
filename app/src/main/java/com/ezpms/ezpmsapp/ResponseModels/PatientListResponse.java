package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.PatientListData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientListResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<PatientListData> patientListData = null;

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

    public List<PatientListData> getPatientListData() {
        return patientListData;
    }

    public void setPatientListData(List<PatientListData> patientListData) {
        this.patientListData = patientListData;
    }
}