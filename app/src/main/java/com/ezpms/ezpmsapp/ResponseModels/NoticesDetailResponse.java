package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.NoticesDetailData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticesDetailResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private NoticesDetailData noticesDetailData;

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

    public NoticesDetailData getNoticesDetailData() {
        return noticesDetailData;
    }

    public void setNoticesDetailData(NoticesDetailData noticesDetailData) {
        this.noticesDetailData = noticesDetailData;
    }
}
