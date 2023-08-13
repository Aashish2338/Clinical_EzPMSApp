package com.ezpms.ezpmsapp.ResponseModels;

import com.ezpms.ezpmsapp.Models.TreatmentDetailPatientData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TreatmentDetailPatientResponse {
    @SerializedName("RespCode")
    @Expose
    private Integer respCode;
    @SerializedName("RespMsg")
    @Expose
    private String respMsg;
    @SerializedName("DATA")
    @Expose
    private TreatmentDetailPatientData treatmentDetailPatientData;

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

    public TreatmentDetailPatientData getTreatmentDetailPatientData() {
        return treatmentDetailPatientData;
    }

    public void setTreatmentDetailPatientData(TreatmentDetailPatientData treatmentDetailPatientData) {
        this.treatmentDetailPatientData = treatmentDetailPatientData;
    }
}
