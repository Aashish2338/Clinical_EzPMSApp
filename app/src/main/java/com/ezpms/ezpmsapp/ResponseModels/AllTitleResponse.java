package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.AllTitleData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTitleResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<AllTitleData> allTitleData = null;

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

    public List<AllTitleData> getAllTitleData() {
        return allTitleData;
    }

    public void setAllTitleData(List<AllTitleData> allTitleData) {
        this.allTitleData = allTitleData;
    }
}
