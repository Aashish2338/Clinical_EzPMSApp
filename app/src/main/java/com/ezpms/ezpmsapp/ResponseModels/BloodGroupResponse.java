package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.BloodGroupData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodGroupResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<BloodGroupData> bloodGroupData = null;

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

    public List<BloodGroupData> getBloodGroupData() {
        return bloodGroupData;
    }

    public void setBloodGroupData(List<BloodGroupData> bloodGroupData) {
        this.bloodGroupData = bloodGroupData;
    }

}
