package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.TimeSloteData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeSlotsResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private List<TimeSloteData> timeSloteData = null;

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

    public List<TimeSloteData> getTimeSloteData() {
        return timeSloteData;
    }

    public void setTimeSloteData(List<TimeSloteData> timeSloteData) {
        this.timeSloteData = timeSloteData;
    }
}
