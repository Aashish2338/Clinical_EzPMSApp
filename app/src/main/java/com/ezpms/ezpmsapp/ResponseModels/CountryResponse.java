package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.CountryData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<CountryData> countryData = null;

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

    public List<CountryData> getData() {
        return countryData;
    }

    public void setData(List<CountryData> countryData) {
        this.countryData = countryData;
    }
}
