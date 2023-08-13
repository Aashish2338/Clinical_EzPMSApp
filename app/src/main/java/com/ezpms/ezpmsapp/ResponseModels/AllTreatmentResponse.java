package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.AllTreatmentData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTreatmentResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<AllTreatmentData> allTreatmentData = null;

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

    public List<AllTreatmentData> getAllTreatmentData() {
        return allTreatmentData;
    }

    public void setAllTreatmentData(List<AllTreatmentData> allTreatmentData) {
        this.allTreatmentData = allTreatmentData;
    }
}
