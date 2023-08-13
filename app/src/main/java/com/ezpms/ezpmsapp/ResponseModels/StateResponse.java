package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.CityData;
import com.ezpms.ezpmsapp.Models.CountryData;
import com.ezpms.ezpmsapp.Models.StateData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<StateData> stateData = null;

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

    public List<StateData> getStateData() {
        return stateData;
    }

    public void setStateData(List<StateData> stateData) {
        this.stateData = stateData;
    }
}
