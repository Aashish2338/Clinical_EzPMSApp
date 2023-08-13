package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.SupportData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<SupportData> supportData = null;

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

    public List<SupportData> getSupportData() {
        return supportData;
    }

    public void setSupportData(List<SupportData> supportData) {
        this.supportData = supportData;
    }
}
