package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.SupportDetailData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportDetailResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private SupportDetailData supportDetailData;

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

    public SupportDetailData getSupportDetailData() {
        return supportDetailData;
    }

    public void setSupportDetailData(SupportDetailData supportDetailData) {
        this.supportDetailData = supportDetailData;
    }
}
